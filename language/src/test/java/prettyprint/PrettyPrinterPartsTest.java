package prettyprint;
import de.monticore.lang.sysmlbasis._ast.ASTSysMLElement;
import de.monticore.lang.sysmlv2.SysMLv2Mill;
import de.monticore.lang.sysmlv2._ast.ASTSysMLModel;
import de.monticore.lang.sysmlv2._parser.SysMLv2Parser;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PrettyPrinterPartsTest {
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

  @ParameterizedTest(name = "{index} - {0} content is preserved after pretty print")
  @ValueSource(strings = { "parts.sysml" })
  public void testRoundTripSemantic(String modelName) throws IOException {
    // Test 2: Does the semantic content match?
    Optional<ASTSysMLModel> ast1 = SysMLv2Mill.parser().parse(MODEL_PATH + "/" + modelName);
    String ppm = SysMLv2Mill.prettyPrint(ast1.get(), true);
    Optional<ASTSysMLModel> ast2 = SysMLv2Mill.parser().parse_String(ppm);

    // Compare the actual content
    assertTrue(
    ast1.get().deepEquals(ast2.get())
);

  }
}
