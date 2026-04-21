package parser;
import static org.junit.jupiter.api.Assertions.*;
import de.monticore.lang.sysmlv2._ast.ASTSysMLModel;
import de.monticore.lang.sysmlbasis._ast.ASTDependency;
import java.io.IOException;
import java.util.Optional;

import de.monticore.lang.sysmlv2._parser.SysMLv2Parser;
import org.junit.jupiter.api.Test;

public class DependencyTest {
  @Test
  public void parsesDependencyFromAndToSeparately() throws IOException {
    String model = "dependency Test from Package1, Package2 to Package3, Package4;";


    SysMLv2Parser parser = new SysMLv2Parser();

    Optional<ASTSysMLModel> rootOpt = parser.parse_StringSysMLModel(model);

    assertTrue(rootOpt.isPresent(), "Expected model to parse");
    ASTSysMLModel root = rootOpt.get();

    ASTDependency dep = root.getSysMLElementList().stream()
        .filter(e -> e instanceof ASTDependency)
        .map(e -> (ASTDependency) e)
        .findFirst()
        .orElseThrow(() -> new AssertionError("Expected an ASTDependency in the parsed model"));

    assertEquals(2, dep.sizeSrc());
    assertEquals("Package1", dep.getSrc(0).toString());
    assertEquals("Package2", dep.getSrc(1).toString());

    assertEquals(2, dep.sizeTgt());
    assertEquals("Package3", dep.getTgt(0).toString());
    assertEquals("Package4", dep.getTgt(1).toString());
  }
}
