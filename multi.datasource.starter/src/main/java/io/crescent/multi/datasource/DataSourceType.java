package io.crescent.multi.datasource;

public enum DataSourceType {
  /**
   * Master
   */
  MASTER("%s"),

  /**
   * slave
   */
  SLAVE("%s-Slave"),

  /**
   * 普通模式，不涉及主备切换
   */
  NORMAL("%s");

  private String pattern;

  DataSourceType(String pattern) {
    this.pattern = pattern;
  }


  public String getDsKey(Object key) {
    return String.format(pattern, key);
  }


}
