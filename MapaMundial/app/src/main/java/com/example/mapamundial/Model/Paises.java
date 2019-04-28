package com.example.mapamundial.Model;

import java.io.Serializable;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;


public class Paises implements Serializable {

    @SerializedName("name")
    public String name;

//    @SerializedName("topLevelDomain")
//    public List<String> topLevelDomain;

    @SerializedName("alpha2Code")
    public String alpha2Code;

    @SerializedName("alpha3Code")
    public String alpha3Code;

//    @SerializedName("callingCodes")
//    public List<String> callingCodes;

    @SerializedName("capital")
    public String capital;

//    @SerializedName("altSpellings")
//    public List<String> altSpellings;

    @SerializedName("region")
    public String region;

    @SerializedName("subregion")
    public String region2;

    @SerializedName("population")
    public String population;
//
    @SerializedName("latlng")
    public List<String> latlng;

    @SerializedName("demonym")
    public String demonym;

    @SerializedName("area")
    public String area;

    @SerializedName("gini")
    public String gini;



//
//    @SerializedName("timezones")
//    public List<String> timezones;

//    @SerializedName("borders")
//    public List<String> borders;

    @SerializedName("nativeName")
    public String nativeName;

    @SerializedName("numericCode")
    public String numericCode;

//    @SerializedName("currencies")
//    public List<String> currencies;
//
//    @SerializedName("languages")
//    public List<String> languages;
//
//    @SerializedName("translations")
//    public List<String> translations;

    @SerializedName("relevance")
    public String relevance;

    public Paises(String name, String region2) {
        this.name = name;
        this.region2 = region2;
    }

    public Paises(String name, String capital, String region, String region2, String population, String area, String numericCode) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.region2 = region2;
        this.population = population;
        this.area = area;
        this.numericCode = numericCode;
    }

    @Override
    public String toString() {
        return "Paises{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public String getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }

    public String getRegion2() {
        return region2;
    }

    public String getPopulation() {
        return population;
    }

    public List<String> getLatlng() {
        return latlng;
    }

    public String getDemonym() {
        return demonym;
    }

    public String getArea() {
        return area;
    }

    public String getGini() {
        return gini;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public String getRelevance() {
        return relevance;
    }
}
