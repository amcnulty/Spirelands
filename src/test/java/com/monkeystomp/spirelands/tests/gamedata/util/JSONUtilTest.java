package com.monkeystomp.spirelands.tests.gamedata.util;

import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Aaron Michael McNulty
 */
public class JSONUtilTest {
  private JSONUtil jsonUtil = new JSONUtil();
  private JSONParser parser = new JSONParser();
  private JSONObject json;
  
  public JSONUtilTest() {
    loadTestJson();
  }
  
  private void loadTestJson() {
    try {
      json = (JSONObject) parser.parse(new FileReader(getClass().getResource("/testJson.json").getFile()));
    } catch (IOException | ParseException ex) {
      Logger.getLogger(JSONUtilTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  @Test
  public void testDataLoaded() {
    Assert.assertNotNull(json);
  }
  
  @Test
  public void getNestedObjectTest() {
    JSONObject level1 = jsonUtil.getNestedObject(json, new String[]{"TestData", "level1"});
    Assert.assertNotNull(level1);
    JSONObject level2 = jsonUtil.getNestedObject(json, new String[]{"TestData", "level1", "level2"});
    Assert.assertNotNull(level2);
    JSONObject level3 = jsonUtil.getNestedObject(json, new String[]{"TestData", "level1", "level2", "level3"});
    Assert.assertNotNull(level3);
  }
  
  @Test
  public void getNestedStringTest() {
    String nestedString = jsonUtil.getNestedString(json, new String[]{"TestData", "level1", "key1"});
    Assert.assertNotNull(nestedString);
    Assert.assertEquals("should equal Test Value", "Test Value", nestedString);
  }
  
  @Test
  public void getNestedIntTest() {
    int nestedInt = jsonUtil.getNestedInt(json, new String[]{"TestData", "level1", "level2", "key2"});
    Assert.assertEquals("should equal 643", 643, nestedInt);
  }
  
  @Test
  public void getNestedBooleanTest() {
    boolean nestedBoolean = jsonUtil.getNestedBoolean(json, new String[]{"TestData", "level1", "level2", "level3", "key3"});
    Assert.assertTrue("should equal true", nestedBoolean);
  }

}
