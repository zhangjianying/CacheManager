package com.zsoftware.cachemanager.processor;

import android.util.Log;

import com.zsoftware.cachemanager.dbhelper.FileDbHelper;
import com.zsoftware.cachemanager.processor.metadata.Metadata;
import com.zsoftware.cachemanager.util.SqlUtils;

public class UpdateProcessor<T>
{
  private static final String DEBUG_TAG = "SaveProcessor";
  private Class<T> _clazz;
  private StringBuilder sqlStringBuilder = new StringBuilder(65);
  private FileDbHelper fileDbHelper;
  private Metadata metadata;

  public UpdateProcessor(Class<T> t, FileDbHelper fileDbHelper)
  {
    this.fileDbHelper = fileDbHelper;
  }

  public UpdateProcessor update(Class<T> clazz, Metadata metadata) {
    this._clazz = clazz;
    this.sqlStringBuilder.append(String.format("update %s", new Object[] { 
      metadata.tableName }));
    this.metadata = metadata;
    return this;
  }

  public UpdateProcessor set(String expression, Object[] value) {
    this.sqlStringBuilder.append(" set ");
    expression = expression.replaceAll("\\?", "%s");
    this.sqlStringBuilder.append(String.format(expression, value));
    return this;
  }

  public UpdateProcessor where(String expression, Object[] value) {
    this.sqlStringBuilder.append(" where ");
    expression = expression.replaceAll("\\?", "%s");
    this.sqlStringBuilder.append(String.format(expression, value));
    return this;
  }

  public void commit() {
    Log.v("SaveProcessor", "commit");
    String sql = this.sqlStringBuilder.toString();
    Log.d("SaveProcessor", "sql=" + sql);
    SqlUtils.execSQL(sql, this.fileDbHelper);
  }
}