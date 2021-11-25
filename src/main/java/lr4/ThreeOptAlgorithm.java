package lr4;

public class ThreeOptAlgorithm {

    public static Tour run(Tour route)
    {
        float distance = route.getDistance();

        for (int i = 1; i < route.tourSize()-3; ++i)
        {
            for (int j = i+1; j < route.tourSize()-2; ++j)
            {
                for (int k = j+1; k < route.tourSize()-1; ++k)
                {
                    // Perform the 3 way swap and test the length
                    route.swapIndexes(i, k);
                    route.swapIndexes(j, k);
                    float newDistance = route.getDistance();

                    if (newDistance < distance)
//                        return run(route);
                        return route;
                    else
                    {
                        route.swapIndexes(j, k);
                        route.swapIndexes(i, k);
                    }
                }
            }
        }

        return route;
    }


}
