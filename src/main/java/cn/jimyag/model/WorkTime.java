package cn.jimyag.model;

import java.util.Objects;

public class WorkTime {
    private int day;//星期几
    private int serialNumber;//第几节课


    public WorkTime(int day, int serialNumber) {
        this.day = day;
        this.serialNumber = serialNumber;
    }

    public WorkTime() {
        this(1,1);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    //判断两个工作时间是否冲突
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkTime workTime = (WorkTime) o;
        return day == workTime.day && serialNumber == workTime.serialNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, serialNumber);
    }

    @Override
    public String toString() {
        return "\ncn.jimyag.model.WorkTime{" +
                "day=" + day +
                ", serialNumber=" + serialNumber +
                "}";
    }
}
