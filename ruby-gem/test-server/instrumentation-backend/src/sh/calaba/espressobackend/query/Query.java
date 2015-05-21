package sh.calaba.espressobackend.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.hamcrest.Matchers;

import sh.calaba.espressobackend.query.antlr.UIQueryLexer;
import sh.calaba.espressobackend.query.antlr.UIQueryParser;
import sh.calaba.espressobackend.query.ast.InvalidUIQueryException;
import sh.calaba.espressobackend.query.ast.UIQueryAST;
import sh.calaba.espressobackend.query.ast.UIQueryASTClassName;
import sh.calaba.espressobackend.query.ast.UIQueryASTPredicate;
import sh.calaba.espressobackend.query.ast.UIQueryASTWith;
import sh.calaba.espressobackend.query.ast.UIQueryDirection;
import sh.calaba.espressobackend.query.ast.UIQueryEvaluator;
import sh.calaba.espressobackend.query.ast.UIQueryVisibility;
import sh.calaba.espressobackend.query.espresso.AllRootsCaptorMatcher;
import sh.calaba.espressobackend.query.espresso.AllViewsCaptorMatcher;
import sh.calaba.espressobackend.query.espresso.ViewCaptor;
import sh.calaba.espressobackend.query.InvocationOperation;
import sh.calaba.espressobackend.query.Operation;
import sh.calaba.espressobackend.query.PropertyOperation;
import sh.calaba.espressobackend.query.QueryResult;
import android.view.View;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.Root;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Query {

	private String queryString;
	@SuppressWarnings("rawtypes")
	private List operations;

	public Query(String queryString) {
		this.queryString = queryString;
		this.operations = Collections.EMPTY_LIST;
		if (this.queryString == null || this.queryString.trim().equals("")) {
			throw new IllegalArgumentException("Illegal query: "
					+ this.queryString);
		}
	}

	@SuppressWarnings("rawtypes")
	public Query(String queryString, List args) {
		this(queryString);
		this.operations = args;
	}

	public QueryResult executeQuery() {
		return UIQueryEvaluator.evaluateQueryWithOptions(
				parseQuery(this.queryString), rootViews(),
				parseOperations(this.operations));
	}

	@SuppressWarnings("rawtypes")
	public static List<Operation> parseOperations(List ops) {
		List<Operation> result = new ArrayList<Operation>(ops.size());
		for (Object o : ops) {
			Operation op = null;
			if (o instanceof Operation) {
				op = (Operation) o;
			} else if (o instanceof String) {
				op = new PropertyOperation((String) o);
			} else if (o instanceof Map) {
				Map mapOp = (Map) o;
				String methodName = (String) mapOp.get("method_name");
				if (methodName == null) {
					throw new IllegalArgumentException(
							"Trying to convert a Map without method_name to an operation. "
									+ mapOp.toString());
				}
				List arguments = (List) mapOp.get("arguments");
				if (arguments == null) {
					throw new IllegalArgumentException(
							"Trying to convert a Map without arguments to an operation. "
									+ mapOp.toString());
				}
				op = new InvocationOperation(methodName, arguments);
			}
			result.add(op);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static List<UIQueryAST> parseQuery(String query) {
		UIQueryLexer lexer = new UIQueryLexer(new ANTLRStringStream(query));
		UIQueryParser parser = new UIQueryParser(new CommonTokenStream(lexer));

		UIQueryParser.query_return q;
		try {
			q = parser.query();
		} catch (RecognitionException e) {
			throw new InvalidUIQueryException(e.getMessage());
		}
		if (q == null) {
			throw new InvalidUIQueryException(query);
		}
		CommonTree rootNode = (CommonTree) q.getTree();
		List<CommonTree> queryPath = rootNode.getChildren();

		if (queryPath == null || queryPath.isEmpty()) {
			queryPath = Collections.singletonList(rootNode);
		}

		return mapUIQueryFromAstNodes(queryPath);
	}

	public static List<UIQueryAST> mapUIQueryFromAstNodes(List<CommonTree> nodes) {
		List<UIQueryAST> mapped = new ArrayList<UIQueryAST>(nodes.size());
		for (CommonTree t : nodes) {
			mapped.add(uiQueryFromAst(t));
		}
		return mapped;
	}

	public static UIQueryAST uiQueryFromAst(CommonTree step) {
		String stepType = UIQueryParser.tokenNames[step.getType()];
		switch (step.getType()) {
		case UIQueryParser.QUALIFIED_NAME:
			try {
				return new UIQueryASTClassName(Class.forName(step.getText()));
			} catch (ClassNotFoundException e) {
				return new UIQueryASTClassName((String) null);
			}
		case UIQueryParser.NAME:
			return new UIQueryASTClassName(step.getText());

		case UIQueryParser.WILDCARD:
			try {
				return new UIQueryASTClassName(
						Class.forName("android.view.View"));
			} catch (ClassNotFoundException e) {
				// Cannot happen
				throw new IllegalStateException(e);
			}

		case UIQueryParser.FILTER_COLON:
			return UIQueryASTWith.fromAST(step);

		case UIQueryParser.ALL:
			return UIQueryVisibility.ALL;

		case UIQueryParser.VISIBLE:
			return UIQueryVisibility.VISIBLE;

		case UIQueryParser.BEGINPRED:
			return UIQueryASTPredicate.newPredicateFromAST(step);
		case UIQueryParser.DIRECTION:
			return UIQueryDirection.valueOf(step.getText().toUpperCase());

		default:
			throw new InvalidUIQueryException("Unknown query: " + stepType
					+ " with text: " + step.getText());

		}

	}

	public List<View> rootViews() {
		Set<View> parents = new HashSet<View>();
		AllViewsCaptorMatcher viewCaptor = new AllViewsCaptorMatcher();
        AllRootsCaptorMatcher allRootsCaptorMatcher = new AllRootsCaptorMatcher();
		Espresso.onView(viewCaptor).inRoot(allRootsCaptorMatcher).perform(new ViewCaptor());

        for (Root root : allRootsCaptorMatcher.getCapturedViews()) {
            /*Espresso.onView(viewCaptor).inRoot(equalTo(root)).perform(new ViewCaptor());
            for (View v : viewCaptor.getCapturedViews()) {
                View parent = getTopParent(v);
                parents.add(parent);
            }*/
            parents.add(root.getDecorView());
        }
		List<View> results = new ArrayList<View>(parents);
		return results;
	}
	
	/**
	 * Returns the absolute top parent {@code View} in for a given {@code View}.
	 *
	 * @param view the {@code View} whose top parent is requested
	 * @return the top parent {@code View}
	 */

	public View getTopParent(View view) {
		if (view.getParent() != null
				&& view.getParent() instanceof android.view.View) {
			return getTopParent((View) view.getParent());
		} else {
			return view;
		}
	}

}
