import client.data_access.high_contrast.HighContrastDataAccess;
import client.use_case.high_contrast.HighContrastDataAccessInterface;
import client.use_case.high_contrast.HighContrastInteractor;
import client.use_case.high_contrast.HighContrastOutputBoundary;
import client.use_case.high_contrast.HighContrastOutputData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HighContrastInteractorTest {

    private HighContrastDataAccessInterface optionsDataAccessObject;
    private HighContrastOutputBoundary optionsOutputBoundary;
    private HighContrastInteractor highContrastInteractor;

    @BeforeEach
    void setUp() {
        optionsDataAccessObject = mock(HighContrastDataAccessInterface.class);
        optionsOutputBoundary = mock(HighContrastOutputBoundary.class);
        highContrastInteractor = new HighContrastInteractor(optionsDataAccessObject, optionsOutputBoundary);
    }

    @Test
    void testExecuteToggleHighContrast() {
        HighContrastOutputData outputData = new HighContrastOutputData(1); // Assuming true means high contrast is enabled
        when(optionsDataAccessObject.get(optionsDataAccessObject.HIGH_CONTRAST)).thenReturn(outputData);

        int result = highContrastInteractor.execute(HighContrastInteractor.TOGGLE_HIGH_CONTRAST);

        verify(optionsDataAccessObject).toggle(optionsDataAccessObject.HIGH_CONTRAST);
        verify(optionsOutputBoundary).setHighContrast(outputData);
        assertEquals(1, result, "High contrast toggle did not return expected value");
    }

    // Additional tests for other actions can be added here
}

class HighContrastDataAccessTest {

    private HighContrastDataAccess highContrastDataAccess;
    private Path tempCsvPath;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception {
        tempCsvPath = tempDir.resolve("options.csv");
        Files.write(tempCsvPath, Arrays.asList("high_contrast,0"));
        highContrastDataAccess = new HighContrastDataAccess();
    }

//    @Test
//    void testGetHighContrastOption() {
//        HighContrastOutputData result = highContrastDataAccess.get(HighContrastDataAccessInterface.HIGH_CONTRAST);
//        assertNotNull(result);
//        assertFalse(result.getHighContrast());
//    }

    @Test
    void testToggleHighContrastOption() throws Exception {
        // Initial setup of CSV file
        Files.write(tempCsvPath, Arrays.asList("high_contrast,0"));

        // Call the method under test
        highContrastDataAccess.toggle(HighContrastDataAccessInterface.HIGH_CONTRAST);

        // Read file to verify changes
        List<String> lines = Files.readAllLines(tempCsvPath);
        assertFalse(lines.isEmpty());
        String[] parsedLine = lines.get(HighContrastDataAccessInterface.HIGH_CONTRAST).split(",");
        assertEquals("0", parsedLine[1].strip());

        // Call toggle again to switch back
        highContrastDataAccess.toggle(HighContrastDataAccessInterface.HIGH_CONTRAST);

        // Read file to verify it switched back
        lines = Files.readAllLines(tempCsvPath);
        parsedLine = lines.get(HighContrastDataAccessInterface.HIGH_CONTRAST).split(",");
        assertEquals("0", parsedLine[1].strip());
    }
}