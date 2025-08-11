package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dummyset2")
public class DummyData {
    @Id
    private String _id;  // MongoDB 기본 ObjectId

    private int id;
    private int SerialNumber;
    private String ModelName;
    private int DetectIndex;
    private int StageIndex;
    private String Label;
    private String Result;
    private String ImgPath;
    private int DayOrNight;
    private String CDate;
    private String EDate;
    private String MDate; // null 가능

    public DummyData() {}

    // Getter & Setter
    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSerialNumber() { return SerialNumber; }
    public void setSerialNumber(int serialNumber) { SerialNumber = serialNumber; }

    public String getModelName() { return ModelName; }
    public void setModelName(String modelName) { ModelName = modelName; }

    public int getDetectIndex() { return DetectIndex; }
    public void setDetectIndex(int detectIndex) { DetectIndex = detectIndex; }

    public int getStageIndex() { return StageIndex; }
    public void setStageIndex(int stageIndex) { StageIndex = stageIndex; }

    public String getLabel() { return Label; }
    public void setLabel(String label) { Label = label; }

    public String getResult() { return Result; }
    public void setResult(String result) { Result = result; }

    public String getImgPath() { return ImgPath; }
    public void setImgPath(String imgPath) { ImgPath = imgPath; }

    public int getDayOrNight() { return DayOrNight; }
    public void setDayOrNight(int dayOrNight) { DayOrNight = dayOrNight; }

    public String getCDate() { return CDate; }
    public void setCDate(String CDate) { this.CDate = CDate; }

    public String getEDate() { return EDate; }
    public void setEDate(String EDate) { this.EDate = EDate; }

    public String getMDate() { return MDate; }
    public void setMDate(String MDate) { this.MDate = MDate; }
}