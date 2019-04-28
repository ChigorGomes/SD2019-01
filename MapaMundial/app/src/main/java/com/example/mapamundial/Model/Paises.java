package com.example.mapamundial.Model;

import java.io.Serializable;
import java.util.List;

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
    public String subregion;

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

    public Paises(String name, String capital, String region, String population, String area) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.population = population;
        this.area = area;
    }
//
//    public Paises(String name, String capital, String region, String population, List<String> latlng, String area) {
//        this.name = name;
//        this.capital = capital;
//        this.region = region;
//        this.population = population;
//        this.latlng = latlng;
//        this.area = area;
//    }
//
    @Override
    public String toString() {
        return "Paises{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                '}';
    }


}
