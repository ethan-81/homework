package com.homework.mpay.account.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class PointWindow {
    @Builder.Default private final List<Point> points = new ArrayList<>();

    public List<Point> getPoints() {
        return Collections.unmodifiableList(this.points);
    }

    public int getTotalAvailableAmountByPointTypeCode(String pointTypeCode) {
        return this.points.stream()
                .filter(point -> pointTypeCode.equals(point.getPointTypeCode()))
                .mapToInt(Point::getAvailableAmount)
                .sum();
    }

    public Optional<Point> findPointByPointId(String pointId) {
        return this.points.stream().filter(point -> point.getPointId().equals(pointId)).findFirst();
    }

    public List<Point> getSortedPointsForUse() {
        return this.points.stream()
                .sorted(Comparator.comparing(Point::getUsePriority).thenComparing(Point::getExpireDate))
                .toList();
    }
}
