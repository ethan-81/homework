package com.homework.mpay.account.domain;

import com.homework.mpay.common.constant.TransactionTypeCode;
import java.util.ArrayList;
import java.util.Collections;
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

    public List<Point> getUpdatedPoints() {
        return this.points.stream()
                .filter(point -> !point.getTransactionTypeCode().equals(TransactionTypeCode.NONE))
                .toList();
    }

    public int getTotalAvailableAmountByPointTypeCode(String pointTypeCode) {
        return this.points.stream()
                .filter(point -> pointTypeCode.equals(point.getPointTypeCode()))
                .mapToInt(Point::getAvailableAmount)
                .sum();
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public Optional<Point> findPointByPointId(String pointId) {
        return this.points.stream().filter(point -> point.getPointId().equals(pointId)).findFirst();
    }

    public void updatePoint(Point updatedPoint) {
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).getPointId().equals(updatedPoint.getPointId())) {
                points.set(i, updatedPoint);
                break;
            }
        }
    }
}
