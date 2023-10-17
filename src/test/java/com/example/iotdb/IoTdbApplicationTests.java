package com.example.iotdb;

import lombok.extern.slf4j.Slf4j;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.Session;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootTest
@Slf4j
class IoTdbApplicationTests {
  private static Session session;

  public Session getSession() throws IoTDBConnectionException {
    if (session == null) {
      log.info("正在连接iotdb.......");
      session = new Session.Builder().host("127.0.0.1").port(6667).username("root").password("root").build();
      session.open(false);
      session.setFetchSize(100);
      log.info("iotdb连接成功~");
      // 设置时区
    }
    return session;
  }
  public void close() throws IoTDBConnectionException {
    session.close();
    log.info("关闭");
  }


  @Test
  void contextLoads() throws IoTDBConnectionException {
    getSession();

    close();
  }
  @Test
  void test1() throws IoTDBConnectionException {
    getSession();
    try {
      session.setStorageGroup("root.test.test01");
    } catch (StatementExecutionException e) {
      e.printStackTrace();
    }
    close();
  }
  @Test
  void  test2() {
    String filePath = "D:\\编程\\java\\IoTDB\\src\\test\\java\\com\\example\\iotdb\\1.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        // 使用split方法按照'|'进行拆分
        String[] parts = line.split("\\|");

        // 打印拆分后的结果
        for (String part : parts) {
          System.out.println(part);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
