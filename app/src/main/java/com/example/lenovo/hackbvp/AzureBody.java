package com.example.lenovo.hackbvp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Lenovo on 11-10-2017.
 */

public class AzureBody {
    @SerializedName("Inputs")
    Inputs inputs;

    public static class Inputs {
        public Inputs(ArrayList<TrainingData> inputs1) {
            this.inputs1 = inputs1;
        }

        ArrayList<TrainingData> inputs1;

        public void setInputs1(ArrayList<TrainingData> inputs1) {
            this.inputs1 = inputs1;
        }

        public ArrayList<TrainingData> getInputs1() {
            return inputs1;
        }

    }

    public Inputs getInputs() {
        return inputs;
    }

    public void setInputs(Inputs inputs) {
        this.inputs = inputs;
    }
}
