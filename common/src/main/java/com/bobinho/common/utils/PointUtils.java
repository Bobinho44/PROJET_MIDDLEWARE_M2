package com.bobinho.common.utils;

import java.awt.*;

public class PointUtils {

	public static Point translate(Point from, int translation) {
		return new Point(from.x + translation, from.y + translation);
	}

	public static boolean isBetween(Point p1, Point p2, Point p3) {
		return p3.x >= p1.x && p3.x <= p2.x && p3.y >= p1.y && p3.y <= p2.y;
	}

	public static boolean isBetween(int from, int to, int tested) {
		return tested >= from && tested <= to;
	}

	public static boolean isValidIndex(int x, int y, int maxX, int maxY) {
		return x >= 0 && y >= 0 && x < maxX && y < maxY;
	}

}