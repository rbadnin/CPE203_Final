
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.List;

interface PathingStrategy
{
   List<Point> computePath(Point start, Point end,
      Predicate<Point> canPassThrough,
      BiPredicate<Point, Point> withinReach,
      Function<Point, Stream<Point>> potentialNeighbors);

   Function<Point, Stream<Point>> CARDINAL_NEIGHBORS =
      point ->
         Stream.<Point>builder()
            .add(new Point(point.getX(), point.getY() - 1))
            .add(new Point(point.getX(), point.getY() + 1))
            .add(new Point(point.getX() - 1, point.getY()))
            .add(new Point(point.getX() + 1, point.getY()))
            .build();
}