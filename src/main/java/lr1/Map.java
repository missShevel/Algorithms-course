package lr1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Map {
    public static HashMap<String, Integer> listOfRegions;
    private int[][] regionsRelations;
    private List<String> colors;
    private List<String> regions;


    public static void setListOfRegions(HashMap<String, Integer> listOfRegions) {
        Map.listOfRegions = listOfRegions;
    }

    public int[][] getRegionsRelations() {
        return regionsRelations;
    }

    public void setRegionsRelations(int[][] regionsRelations) {
        this.regionsRelations = regionsRelations;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List regions) {
        this.regions = regions;
    }

    public static HashMap<String, Integer> getListOfRegions() {
        return listOfRegions;
    }

    public Map() {
        this.regionsRelations = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0}
        };
        this.colors = Arrays.asList("Red", "Green", "Blue", "Yellow");
        this.regions = Arrays.asList(
                "Cherkasy", "Chernihiv", "Chernivtsi", "Dnipro", "Donetsk", "Ivano-Frankivsk", "Kharkiv",
                "Kherson", "Khmelnytskyi", "Kyiv", "Kropyvnytskiy", "Luhansk", "Lviv", "Mykolaiv", "Odesa", "Poltava", "Rivne",
                "Sumy", "Ternopil", "Vinnytsia", "Volyn", "Zakarpattia", "Zaporizhzhia", "Zhytomyr");
        this.listOfRegions = new HashMap<>();
        setListOfRegions();
    }

    public void setListOfRegions() {
        this.listOfRegions.put("Cherkasy", 0);
        this.listOfRegions.put("Chernihiv", 1);
        this.listOfRegions.put("Chernivtsi", 2);
        this.listOfRegions.put("Dnipro", 3);
        this.listOfRegions.put("Donetsk", 4);
        this.listOfRegions.put("Ivano-Frankivsk", 5);
        this.listOfRegions.put("Kharkiv", 6);
        this.listOfRegions.put("Kherson", 7);
        this.listOfRegions.put("Khmelnytskyi", 8);
        this.listOfRegions.put("Kyiv", 9);
        this.listOfRegions.put("Kropyvnytskiy", 10);
        this.listOfRegions.put("Luhansk", 11);
        this.listOfRegions.put("Lviv", 12);
        this.listOfRegions.put("Mykolaiv", 13);
        this.listOfRegions.put("Odesa", 14);
        this.listOfRegions.put("Poltava", 15);
        this.listOfRegions.put("Rivne", 16);
        this.listOfRegions.put("Sumy", 17);
        this.listOfRegions.put("Ternopil", 18);
        this.listOfRegions.put("Vinnytsia", 19);
        this.listOfRegions.put("Volyn", 20);
        this.listOfRegions.put("Zakarpattia", 21);
        this.listOfRegions.put("Zaporizhzhia", 22);
        this.listOfRegions.put("Zhytomyr", 23);
    }

    public static HashMap<Integer, String> numberAndRegion() {
        HashMap<Integer, String> res = new HashMap<>();
        res.put(0, "Cherkasy");
        res.put(1, "Chernihiv");
        res.put(2, "Chernivtsi");
        res.put(3, "Dnipro");
        res.put(4, "Donetsk");
        res.put(5, "Ivano-Frankivsk");
        res.put(6, "Kharkiv");
        res.put(7, "Kherson");
        res.put(8, "Khmelnytskyi");
        res.put(9, "Kyiv");
        res.put(10, "Kropyvnytskiy");
        res.put(11, "Luhansk");
        res.put(12, "Lviv");
        res.put(13, "Mykolaiv");
        res.put(14, "Odesa");
        res.put(15, "Poltava");
        res.put(16, "Rivne");
        res.put(17, "Sumy");
        res.put(18, "Ternopil");
        res.put(19, "Vinnytsia");
        res.put(20, "Volyn");
        res.put(21, "Zakarpattia");
        res.put(22, "Zaporizhzhia");
        res.put(23, "Zhytomyr");
        return res;
    }
}
