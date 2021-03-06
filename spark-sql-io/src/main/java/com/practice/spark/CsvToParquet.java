package com.practice.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


/**
 * Created by Srinivas Reddy Alluri on 16-09-2016.
 *
 * Usage: java CsvToParquet <app master> <input path> <output path>
 * Ex: java CsvToParquet local file.csv input.parquet
 */
public class CsvToParquet {
    public static void main(String[] args) {

        if ("local".equalsIgnoreCase(args[0])) {
            System.setProperty("spark.sql.warehouse.dir", "..\\..\\spark-wh");
            System.setProperty("hadoop.home.dir", "..\\..\\");
        }


        SparkSession session = SparkSession.builder()
                .master(args[0])
                .appName(CsvToParquet.class.getSimpleName())
                .getOrCreate();
        Dataset<Row> df = session.read()
                .format("com.databricks.spark.csv")
                .option("header", "true")
                .option("inferSchema", "true")
                .option("mode", "DROPMALFORMED")
                .load(args[1]);


        df.write().parquet(args[2]);
    }
}
