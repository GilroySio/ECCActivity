package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.mockingDetails;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockedStatic;

@ExtendWith(MockitoExtension.class)
public class MenuServicesTest {

    MenuServicesImpl ms;
    ByteArrayInputStream mockInput;
    Scanner scanner;
    ArrayList<ArrayList<Pair>> testArray;

    @Mock
    PairServicesImpl mockPairServices;

    MockedStatic<FileUtil> mockFileUtil;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.initMocks(this);
        ms = new MenuServicesImpl();

        mockFileUtil = mockStatic(FileUtil.class);
        
        testArray = new ArrayList<ArrayList<Pair>>();
        testArray.add(new ArrayList<Pair>());
        testArray.get(0).add(new Pair("key", "val"));
        testArray.get(0).add(new Pair("eee", "asd"));
        testArray.add(new ArrayList<Pair>());
        testArray.get(1).add(new Pair("123", "456"));
        testArray.get(1).add(new Pair("krb", "o12"));
    }

    @AfterEach
    void cleanUp() {
        mockFileUtil.close();
    }

    @Test
    public void verifyMenuTest() {
        assertTrue(ms.verifyMenu("1"));
        assertTrue(ms.verifyMenu("2"));
        assertTrue(ms.verifyMenu("3"));
        assertTrue(ms.verifyMenu("4"));
        assertTrue(ms.verifyMenu("5"));
        assertTrue(ms.verifyMenu("6"));
        assertTrue(ms.verifyMenu("x"));
        assertFalse(ms.verifyMenu(""));
        assertFalse(ms.verifyMenu("a"));
        assertFalse(ms.verifyMenu("k"));
        assertFalse(ms.verifyMenu("7"));
    }

    @Test
    public void getMenuTest() {
        mockInput = new ByteArrayInputStream("1\n2\n3\np\nx".getBytes());
        scanner = new Scanner(mockInput);

        assertEquals("1", ms.getMenuInput(scanner));
        assertEquals("2", ms.getMenuInput(scanner));
        assertEquals("3", ms.getMenuInput(scanner));
        assertEquals("x", ms.getMenuInput(scanner));
    }

    @Test 
    public void menuInputTest() {
        assertEquals(1, ms.convertMenuInputToInt("1"));
        assertEquals(2, ms.convertMenuInputToInt("2"));
        assertEquals(3, ms.convertMenuInputToInt("3"));
        assertEquals(4, ms.convertMenuInputToInt("4"));
        assertEquals(5, ms.convertMenuInputToInt("5"));
        assertEquals(6, ms.convertMenuInputToInt("6"));
        assertEquals(7, ms.convertMenuInputToInt("x"));
    }

    @Test
    public void initializeArrayTest() {
        assertTrue(mockingDetails(FileUtil.class).isMock());

        int[] sampleData = {1, 2};

        mockFileUtil.when(() -> FileUtil.readFromFile(any(Scanner.class), any(Random.class), any(String.class))).thenReturn(testArray);

        when(mockPairServices.reset(any(Scanner.class))).thenReturn(sampleData);
        when(mockPairServices.generate(any(Integer.class), any(Integer.class), any(Random.class))).thenReturn(testArray);

        assertEquals(testArray, ms.initializeArray(new String[] {"string"}, new Scanner(System.in), new Random(), mockPairServices));
        //assertEquals(FileUtil.readFromFile(new Scanner(System.in), new Random(), "string"), testArray);

        mockFileUtil.verify(() -> FileUtil.readFromFile(any(Scanner.class), any(Random.class), any(String.class)));

        assertEquals(testArray, ms.initializeArray(new String[] {}, new Scanner(System.in), new Random(), mockPairServices));

        verify(mockPairServices).reset(any(Scanner.class));
        verify(mockPairServices).generate(any(Integer.class), any(Integer.class), any(Random.class));
    }

    @Test
    public void singleMenuActionSearchTest() {
        ArrayList<ArrayList<Integer>> sampleData = new ArrayList<ArrayList<Integer>>();
        sampleData.add(new ArrayList<Integer>(List.of(1, 2, 3)));

        //PairServices mockPairServices = mock(PairServices.class);
        when(mockPairServices.getSearch(any(Scanner.class))).thenReturn("string");
        when(mockPairServices.search(any(ArrayList.class), any(String.class))).thenReturn(sampleData);
        
        ms.singleMenuAction(1, testArray, new Scanner(System.in), null, mockPairServices);

        verify(mockPairServices).getSearch(any(Scanner.class));
        verify(mockPairServices).search(any(ArrayList.class), any(String.class));
        verify(mockPairServices).printSearchResults(any(ArrayList.class));
    }

    @Test
    public void singleMenuActionEditTest() {
        String[] sampleData = {"sam", "ple"};

        when(mockPairServices.getEdit(any(ArrayList.class), any(Scanner.class))).thenReturn("string");
        when(mockPairServices.edit(any(ArrayList.class), any(String.class), any(Scanner.class))).thenReturn(sampleData);

        ms.singleMenuAction(2, testArray, new Scanner(System.in), null, mockPairServices);

        verify(mockPairServices).getEdit(any(ArrayList.class), any(Scanner.class));
        verify(mockPairServices).edit(any(ArrayList.class), any(String.class), any(Scanner.class));
        verify(mockPairServices).printEditResults(any(String[].class));
    }

    @Test
    public void singleMenuActionPrintTest() {
        ms.singleMenuAction(3, testArray, null, null, mockPairServices);

        verify(mockPairServices).printArray(any(ArrayList.class));
    }

    @Test
    public void singleMenuActionResetTest() {
        int[] sampleData = {1, 2};

        when(mockPairServices.reset(any(Scanner.class))).thenReturn(sampleData);
        when(mockPairServices.generate(any(Integer.class), any(Integer.class), any(Random.class))).thenReturn(testArray);

        ms.singleMenuAction(4, testArray, new Scanner(System.in), new Random(), mockPairServices);

        verify(mockPairServices).reset(any(Scanner.class));
        verify(mockPairServices).generate(any(Integer.class), any(Integer.class), any(Random.class));
    }

    @Test
    public void singleMenuActionSortTest() {
        when(mockPairServices.getSort(any(Scanner.class))).thenReturn("string");

        ms.singleMenuAction(5, testArray, new Scanner(System.in), null, mockPairServices);

        verify(mockPairServices).getSort(any(Scanner.class));
        verify(mockPairServices).sort(any(ArrayList.class), any(String.class));
    }

    @Test
    public void singleMenuActionNewColumnTest() {
        ms.singleMenuAction(6, testArray, null, null, mockPairServices);

        verify(mockPairServices).addNewColumn(any(ArrayList.class));
    }

    @Test
    public void closingAppTest() {
        assertTrue(mockingDetails(FileUtil.class).isMock());

        mockInput = new ByteArrayInputStream("sample.txt".getBytes());
        scanner = new Scanner(mockInput);

        ms.closingApp(new String[] {"String"}, testArray, new Scanner(System.in));
        ms.closingApp(new String[] {}, testArray, scanner);

        mockFileUtil.verify(() -> FileUtil.writeToFile(any(ArrayList.class), any(String.class)), times(2));
    }

}