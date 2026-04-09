/* (c) https://github.com/MontiCore/monticore */
package parser;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.lang.sysmlbasis._ast.ASTSysMLElement;
import de.monticore.lang.sysmlv2.SysMLv2Mill;
import de.monticore.lang.sysmlv2._ast.ASTSysMLModel;
import de.monticore.lang.sysmlv2._parser.SysMLv2Parser;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.ast.ASTNode;
import java.io.IOException;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testet, ob von uns gebrauchte Modelle geparst werden können
 */


public class ParserTest {

  private void printAST(Object node, String indent) {
    if (node == null) return;

    System.out.println(indent + node.getClass().getSimpleName());

    // Iterate over all getters that return lists (child nodes)
    for (java.lang.reflect.Method method : node.getClass().getMethods()) {
      if (method.getName().startsWith("get")
          && java.util.List.class.isAssignableFrom(method.getReturnType())) {
        try {
          List<?> children = (List<?>) method.invoke(node);
          for (Object child : children) {
            printAST(child, indent + "  ");
          }
        } catch (Exception e) {
          // ignore inaccessible methods
        }
      }
    }
  }

  private static final String MODEL_PATH = "src/test/resources/parser";

  private SysMLv2Parser parser = SysMLv2Mill.parser();

  @BeforeAll
  public static void init() {
    Log.init();
    SysMLv2Mill.init();
  }

  @BeforeEach
  public void reset() {
    parser.setError(false);
  }

  @ParameterizedTest(name = "{index} - {0} does parse w/o errors")
  @ValueSource(strings = {
      "packages.sysml",
      "imports.sysml",
      "ports.sysml",
      "parts.sysml",
      "states.sysml",
      "parallel_states.sysml",
      "actions.sysml",
      "items.sysml",
      "assert.sysml",
      "constraints.sysml",
      "requirements.sysml",
      "streams.sysml",
      "streamsFilter.sysml",
      "refinement.sysml",
      "cardinalities.sysml",
      "connections.sysml",
      "collections.sysml",
      "StateDecomposition1.sysml",
      "FlowConectionInterfaceExample.sysml",
      "StateActions.sysml",
      "ConditionalSuccessionExample-1.sysml",
      "ifThenElse.sysml",
      "ifThenElse2.sysml",
      "ifThenElse3.sysml",
      "ifThenElse4.sysml",
      "ifThenElse5.sysml",
      "ifThenElse6.sysml"
  })
  public void testParsingModels(String modelName) throws IOException {
    Optional<ASTSysMLModel> ast = SysMLv2Mill.parser().parse(MODEL_PATH  + "/" + modelName);
    assertFalse(parser.hasErrors(), "Parsing should not have failed");
    assertTrue(ast.isPresent(), "The AST should have been created");

    ast.ifPresent(model -> printAST(model, ""));

    ASTSysMLModel model = ast.get();

    for (ASTSysMLElement element : model.getSysMLElementList()) {
      System.out.println(element);
    }
  }
}
