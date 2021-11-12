/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.iotdb.tsfile.test1835;

import org.apache.iotdb.tsfile.file.metadata.TimeseriesMetadata;
import org.apache.iotdb.tsfile.read.TsFileSequenceReader;
import org.apache.iotdb.tsfile.read.common.Path;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.io.IOException;

public class TsFileAggregationV2 {

  private static final String DEVICE1 = "device_";
  public static int chunkNum;
  public static int deviceNum = 1;
  public static int sensorNum = 1;
  public static int fileNum = 1;

  public static void main(String[] args) throws IOException {
    long costTime = 0L;
    Options opts = new Options();
    //    Option chunkNumOption =
    //        OptionBuilder.withArgName("args").withLongOpt("chunkNum").hasArg().create("c");
    //    opts.addOption(chunkNumOption);

    BasicParser parser = new BasicParser();
    CommandLine cl;
    try {
      cl = parser.parse(opts, args);
      //      chunkNum = Integer.parseInt(cl.getOptionValue("c"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    long totalStartTime = System.nanoTime();
    for (int fileIndex = 0; fileIndex < fileNum; fileIndex++) {
      // file path
      String path =
          "/Users/samperson1997/git/iotdb/data/data/sequence/root.sg/1/"
              + deviceNum
              + "/test0.tsfile";

      // aggregation query
      try (TsFileSequenceReader reader = new TsFileSequenceReader(path)) {
        Path seriesPath = new Path(DEVICE1, "sensor_1");
        long startTime = System.nanoTime();
        TimeseriesMetadata timeseriesMetadata = reader.readTimeseriesMetadataV4(seriesPath, false);
        long count = timeseriesMetadata.getStatistics().getCount();
        costTime += (System.nanoTime() - startTime);
        System.out.println(count);
      }
    }
    System.out.println(
        "Total raw read cost time: " + (System.nanoTime() - totalStartTime) / 1000_000 + "ms");
    System.out.println("Index area cost time: " + costTime / 1000_000 + "ms");
  }
}
