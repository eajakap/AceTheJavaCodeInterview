package org.educative.modules.module2.maps;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeMapExampleTest {
    @Test
    void mainPrintsNavigationResultsAndDerivedValues() {
        List<String> lines = ConsoleCapture.linesPrintedBy(() -> TreeMapExample.main(new String[0]));

        assertEquals(List.of(
                "Ceiling key of '15': 20",
                "Ceiling key of '20': 20",
                "Ceiling key of '35': null",
                "Floor key of '25': 20",
                "Floor key of '20': 20",
                "Floor key of '5': null",
                "{apple=4, banana=3, orange=2, pear=1}",
                "Ceiling key of 'apple': apple",
                "Ceiling key of 'banana': banana",
                "Ceiling key of 'pear': pear",
                "Ceiling key of 'appl': apple",
                "Floor key of 'apple': apple",
                "Floor key of 'banana': banana",
                "Floor key of 'pear': pear",
                "Floor key of 'appl': null",
                "Removed value: 4",
                "Value: Not found",
                "Last entry: pear=1",
                "Concert",
                "Top performer: Diana with score 95"
        ), lines);
    }
}
