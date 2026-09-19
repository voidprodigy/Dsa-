class Solution {

    public boolean checkOverlap(
        int radius,
        int xCenter,
        int yCenter,
        int x1,
        int y1,
        int x2,
        int y2
    ) {

        // Find the closest point of the rectangle
        // to the circle's center

        int closestX = xCenter;
        int closestY = yCenter;

        if (xCenter < x1) {
            closestX = x1;
        } else if (xCenter > x2) {
            closestX = x2;
        }

        if (yCenter < y1) {
            closestY = y1;
        } else if (yCenter > y2) {
            closestY = y2;
        }

        // Distance between circle center
        // and closest point of rectangle

        int dx = xCenter - closestX;
        int dy = yCenter - closestY;

        int distanceSquared = dx * dx + dy * dy;

        // If distance <= radius, they overlap
        return distanceSquared <= radius * radius;
    }
}