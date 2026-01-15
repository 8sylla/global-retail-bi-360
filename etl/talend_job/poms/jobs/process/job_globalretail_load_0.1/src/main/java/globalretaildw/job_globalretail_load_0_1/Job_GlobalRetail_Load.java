// ============================================================================
//
// Copyright (c) 2006-2015, Talend SA
//
// Ce code source a été automatiquement généré par_Talend Open Studio for Data Integration
// / Soumis à la Licence Apache, Version 2.0 (la "Licence") ;
// votre utilisation de ce fichier doit respecter les termes de la Licence.
// Vous pouvez obtenir une copie de la Licence sur
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Sauf lorsqu'explicitement prévu par la loi en vigueur ou accepté par écrit, le logiciel
// distribué sous la Licence est distribué "TEL QUEL",
// SANS GARANTIE OU CONDITION D'AUCUNE SORTE, expresse ou implicite.
// Consultez la Licence pour connaître la terminologie spécifique régissant les autorisations et
// les limites prévues par la Licence.

package globalretaildw.job_globalretail_load_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: Job_GlobalRetail_Load Purpose: <br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class Job_GlobalRetail_Load implements TalendJob {

	protected static void logIgnoredError(String message, Throwable cause) {
		System.err.println(message);
		if (cause != null) {
			cause.printStackTrace();
		}

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "Job_GlobalRetail_Load";
	private final String projectName = "GLOBALRETAILDW";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					Job_GlobalRetail_Load.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(Job_GlobalRetail_Load.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tFileInputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputExcel_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputExcel_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputExcel_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputExcel_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_5_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputXML_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row_Cust_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row_Loc_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row_Mgr_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row_Prod_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row_XML_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tFileInputDelimited_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tFileInputDelimited_3_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tFileInputExcel_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tFileInputDelimited_4_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class dim_locationStruct implements routines.system.IPersistableRow<dim_locationStruct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int location_key;

		public int getLocation_key() {
			return this.location_key;
		}

		public String city;

		public String getCity() {
			return this.city;
		}

		public String state;

		public String getState() {
			return this.state;
		}

		public String country;

		public String getCountry() {
			return this.country;
		}

		public String region;

		public String getRegion() {
			return this.region;
		}

		public String market;

		public String getMarket() {
			return this.market;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.location_key;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final dim_locationStruct other = (dim_locationStruct) obj;

			if (this.location_key != other.location_key)
				return false;

			return true;
		}

		public void copyDataTo(dim_locationStruct other) {

			other.location_key = this.location_key;
			other.city = this.city;
			other.state = this.state;
			other.country = this.country;
			other.region = this.region;
			other.market = this.market;

		}

		public void copyKeysDataTo(dim_locationStruct other) {

			other.location_key = this.location_key;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.location_key = dis.readInt();

					this.city = readString(dis);

					this.state = readString(dis);

					this.country = readString(dis);

					this.region = readString(dis);

					this.market = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.location_key = dis.readInt();

					this.city = readString(dis);

					this.state = readString(dis);

					this.country = readString(dis);

					this.region = readString(dis);

					this.market = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.location_key);

				// String

				writeString(this.city, dos);

				// String

				writeString(this.state, dos);

				// String

				writeString(this.country, dos);

				// String

				writeString(this.region, dos);

				// String

				writeString(this.market, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.location_key);

				// String

				writeString(this.city, dos);

				// String

				writeString(this.state, dos);

				// String

				writeString(this.country, dos);

				// String

				writeString(this.region, dos);

				// String

				writeString(this.market, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("location_key=" + String.valueOf(location_key));
			sb.append(",city=" + city);
			sb.append(",state=" + state);
			sb.append(",country=" + country);
			sb.append(",region=" + region);
			sb.append(",market=" + market);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(dim_locationStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.location_key, other.location_key);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];

		public Integer Row_ID;

		public Integer getRow_ID() {
			return this.Row_ID;
		}

		public String Order_ID;

		public String getOrder_ID() {
			return this.Order_ID;
		}

		public java.util.Date Order_Date;

		public java.util.Date getOrder_Date() {
			return this.Order_Date;
		}

		public java.util.Date Ship_Date;

		public java.util.Date getShip_Date() {
			return this.Ship_Date;
		}

		public String Ship_Mode;

		public String getShip_Mode() {
			return this.Ship_Mode;
		}

		public String Customer_ID;

		public String getCustomer_ID() {
			return this.Customer_ID;
		}

		public String Customer_Name;

		public String getCustomer_Name() {
			return this.Customer_Name;
		}

		public String Segment;

		public String getSegment() {
			return this.Segment;
		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public String State;

		public String getState() {
			return this.State;
		}

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Postal_Code;

		public String getPostal_Code() {
			return this.Postal_Code;
		}

		public String Market;

		public String getMarket() {
			return this.Market;
		}

		public String Region;

		public String getRegion() {
			return this.Region;
		}

		public String Product_ID;

		public String getProduct_ID() {
			return this.Product_ID;
		}

		public String Category;

		public String getCategory() {
			return this.Category;
		}

		public String Sub_Category;

		public String getSub_Category() {
			return this.Sub_Category;
		}

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Float Sales;

		public Float getSales() {
			return this.Sales;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public Float Discount;

		public Float getDiscount() {
			return this.Discount;
		}

		public Double Profit;

		public Double getProfit() {
			return this.Profit;
		}

		public Float Shipping_Cost;

		public Float getShipping_Cost() {
			return this.Shipping_Cost;
		}

		public String Order_Priority;

		public String getOrder_Priority() {
			return this.Order_Priority;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Row_ID=" + String.valueOf(Row_ID));
			sb.append(",Order_ID=" + Order_ID);
			sb.append(",Order_Date=" + String.valueOf(Order_Date));
			sb.append(",Ship_Date=" + String.valueOf(Ship_Date));
			sb.append(",Ship_Mode=" + Ship_Mode);
			sb.append(",Customer_ID=" + Customer_ID);
			sb.append(",Customer_Name=" + Customer_Name);
			sb.append(",Segment=" + Segment);
			sb.append(",City=" + City);
			sb.append(",State=" + State);
			sb.append(",Country=" + Country);
			sb.append(",Postal_Code=" + Postal_Code);
			sb.append(",Market=" + Market);
			sb.append(",Region=" + Region);
			sb.append(",Product_ID=" + Product_ID);
			sb.append(",Category=" + Category);
			sb.append(",Sub_Category=" + Sub_Category);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Sales=" + String.valueOf(Sales));
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",Discount=" + String.valueOf(Discount));
			sb.append(",Profit=" + String.valueOf(Profit));
			sb.append(",Shipping_Cost=" + String.valueOf(Shipping_Cost));
			sb.append(",Order_Priority=" + Order_Priority);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer Row_ID;

		public Integer getRow_ID() {
			return this.Row_ID;
		}

		public String Order_ID;

		public String getOrder_ID() {
			return this.Order_ID;
		}

		public java.util.Date Order_Date;

		public java.util.Date getOrder_Date() {
			return this.Order_Date;
		}

		public java.util.Date Ship_Date;

		public java.util.Date getShip_Date() {
			return this.Ship_Date;
		}

		public String Ship_Mode;

		public String getShip_Mode() {
			return this.Ship_Mode;
		}

		public String Customer_ID;

		public String getCustomer_ID() {
			return this.Customer_ID;
		}

		public String Customer_Name;

		public String getCustomer_Name() {
			return this.Customer_Name;
		}

		public String Segment;

		public String getSegment() {
			return this.Segment;
		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public String State;

		public String getState() {
			return this.State;
		}

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Postal_Code;

		public String getPostal_Code() {
			return this.Postal_Code;
		}

		public String Market;

		public String getMarket() {
			return this.Market;
		}

		public String Region;

		public String getRegion() {
			return this.Region;
		}

		public String Product_ID;

		public String getProduct_ID() {
			return this.Product_ID;
		}

		public String Category;

		public String getCategory() {
			return this.Category;
		}

		public String Sub_Category;

		public String getSub_Category() {
			return this.Sub_Category;
		}

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Float Sales;

		public Float getSales() {
			return this.Sales;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public Float Discount;

		public Float getDiscount() {
			return this.Discount;
		}

		public Double Profit;

		public Double getProfit() {
			return this.Profit;
		}

		public Float Shipping_Cost;

		public Float getShipping_Cost() {
			return this.Shipping_Cost;
		}

		public String Order_Priority;

		public String getOrder_Priority() {
			return this.Order_Priority;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Row_ID == null) ? 0 : this.Row_ID.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row1Struct other = (row1Struct) obj;

			if (this.Row_ID == null) {
				if (other.Row_ID != null)
					return false;

			} else if (!this.Row_ID.equals(other.Row_ID))

				return false;

			return true;
		}

		public void copyDataTo(row1Struct other) {

			other.Row_ID = this.Row_ID;
			other.Order_ID = this.Order_ID;
			other.Order_Date = this.Order_Date;
			other.Ship_Date = this.Ship_Date;
			other.Ship_Mode = this.Ship_Mode;
			other.Customer_ID = this.Customer_ID;
			other.Customer_Name = this.Customer_Name;
			other.Segment = this.Segment;
			other.City = this.City;
			other.State = this.State;
			other.Country = this.Country;
			other.Postal_Code = this.Postal_Code;
			other.Market = this.Market;
			other.Region = this.Region;
			other.Product_ID = this.Product_ID;
			other.Category = this.Category;
			other.Sub_Category = this.Sub_Category;
			other.Product_Name = this.Product_Name;
			other.Sales = this.Sales;
			other.Quantity = this.Quantity;
			other.Discount = this.Discount;
			other.Profit = this.Profit;
			other.Shipping_Cost = this.Shipping_Cost;
			other.Order_Priority = this.Order_Priority;

		}

		public void copyKeysDataTo(row1Struct other) {

			other.Row_ID = this.Row_ID;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Row_ID=" + String.valueOf(Row_ID));
			sb.append(",Order_ID=" + Order_ID);
			sb.append(",Order_Date=" + String.valueOf(Order_Date));
			sb.append(",Ship_Date=" + String.valueOf(Ship_Date));
			sb.append(",Ship_Mode=" + Ship_Mode);
			sb.append(",Customer_ID=" + Customer_ID);
			sb.append(",Customer_Name=" + Customer_Name);
			sb.append(",Segment=" + Segment);
			sb.append(",City=" + City);
			sb.append(",State=" + State);
			sb.append(",Country=" + Country);
			sb.append(",Postal_Code=" + Postal_Code);
			sb.append(",Market=" + Market);
			sb.append(",Region=" + Region);
			sb.append(",Product_ID=" + Product_ID);
			sb.append(",Category=" + Category);
			sb.append(",Sub_Category=" + Sub_Category);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Sales=" + String.valueOf(Sales));
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",Discount=" + String.valueOf(Discount));
			sb.append(",Profit=" + String.valueOf(Profit));
			sb.append(",Shipping_Cost=" + String.valueOf(Shipping_Cost));
			sb.append(",Order_Priority=" + Order_Priority);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Row_ID, other.Row_ID);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();
				row2Struct row2 = new row2Struct();
				dim_locationStruct dim_location = new dim_locationStruct();

				/**
				 * [tDBOutput_1 begin ] start
				 */

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "dim_location");
				}

				int tos_count_tDBOutput_1 = 0;

				String dbschema_tDBOutput_1 = null;
				dbschema_tDBOutput_1 = "public";

				String tableName_tDBOutput_1 = null;
				if (dbschema_tDBOutput_1 == null || dbschema_tDBOutput_1.trim().length() == 0) {
					tableName_tDBOutput_1 = ("dim_location");
				} else {
					tableName_tDBOutput_1 = dbschema_tDBOutput_1 + "\".\"" + ("dim_location");
				}

				int nb_line_tDBOutput_1 = 0;
				int nb_line_update_tDBOutput_1 = 0;
				int nb_line_inserted_tDBOutput_1 = 0;
				int nb_line_deleted_tDBOutput_1 = 0;
				int nb_line_rejected_tDBOutput_1 = 0;

				int deletedCount_tDBOutput_1 = 0;
				int updatedCount_tDBOutput_1 = 0;
				int insertedCount_tDBOutput_1 = 0;
				int rowsToCommitCount_tDBOutput_1 = 0;
				int rejectedCount_tDBOutput_1 = 0;

				boolean whetherReject_tDBOutput_1 = false;

				java.sql.Connection conn_tDBOutput_1 = null;
				String dbUser_tDBOutput_1 = null;

				java.lang.Class.forName("org.postgresql.Driver");

				String url_tDBOutput_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "GlobalRetailDW";
				dbUser_tDBOutput_1 = "postgres";

				final String decryptedPassword_tDBOutput_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:0I2qcFijSMCw49tJ7v6RadxyrpOblPweZ1xl4PXe1HtVdygqIQ==");

				String dbPwd_tDBOutput_1 = decryptedPassword_tDBOutput_1;

				conn_tDBOutput_1 = java.sql.DriverManager.getConnection(url_tDBOutput_1, dbUser_tDBOutput_1,
						dbPwd_tDBOutput_1);

				resourceMap.put("conn_tDBOutput_1", conn_tDBOutput_1);
				conn_tDBOutput_1.setAutoCommit(false);
				int commitEvery_tDBOutput_1 = 10000;
				int commitCounter_tDBOutput_1 = 0;

				int batchSize_tDBOutput_1 = 10000;
				int batchSizeCounter_tDBOutput_1 = 0;

				int count_tDBOutput_1 = 0;
				String insert_tDBOutput_1 = "INSERT INTO \"" + tableName_tDBOutput_1
						+ "\" (\"city\",\"state\",\"country\",\"region\",\"market\") VALUES (?,?,?,?,?)";

				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row2");
				}

				int tos_count_tMap_1 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				dim_locationStruct dim_location_tmp = new dim_locationStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tUniqRow_1 begin ] start
				 */

				ok_Hash.put("tUniqRow_1", false);
				start_Hash.put("tUniqRow_1", System.currentTimeMillis());

				currentComponent = "tUniqRow_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row1");
				}

				int tos_count_tUniqRow_1 = 0;

				class KeyStruct_tUniqRow_1 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					String City;
					String State;
					String Country;
					String Market;
					String Region;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.City == null) ? 0 : this.City.hashCode());

							result = prime * result + ((this.State == null) ? 0 : this.State.hashCode());

							result = prime * result + ((this.Country == null) ? 0 : this.Country.hashCode());

							result = prime * result + ((this.Market == null) ? 0 : this.Market.hashCode());

							result = prime * result + ((this.Region == null) ? 0 : this.Region.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_1 other = (KeyStruct_tUniqRow_1) obj;

						if (this.City == null) {
							if (other.City != null)
								return false;

						} else if (!this.City.equals(other.City))

							return false;

						if (this.State == null) {
							if (other.State != null)
								return false;

						} else if (!this.State.equals(other.State))

							return false;

						if (this.Country == null) {
							if (other.Country != null)
								return false;

						} else if (!this.Country.equals(other.Country))

							return false;

						if (this.Market == null) {
							if (other.Market != null)
								return false;

						} else if (!this.Market.equals(other.Market))

							return false;

						if (this.Region == null) {
							if (other.Region != null)
								return false;

						} else if (!this.Region.equals(other.Region))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_1 = 0;
				int nb_duplicates_tUniqRow_1 = 0;
				KeyStruct_tUniqRow_1 finder_tUniqRow_1 = new KeyStruct_tUniqRow_1();
				java.util.Set<KeyStruct_tUniqRow_1> keystUniqRow_1 = new java.util.HashSet<KeyStruct_tUniqRow_1>();

				/**
				 * [tUniqRow_1 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_1", false);
				start_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_1";

				int tos_count_tFileInputDelimited_1 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_1 = 0;
				int footer_tFileInputDelimited_1 = 0;
				int totalLinetFileInputDelimited_1 = 0;
				int limittFileInputDelimited_1 = -1;
				int lastLinetFileInputDelimited_1 = -1;

				char fieldSeparator_tFileInputDelimited_1[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ";").length() > 0) {
					fieldSeparator_tFileInputDelimited_1 = ((String) ";").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_1[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_1 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_1 = /** Start field tFileInputDelimited_1:FILENAME */
						"C:/Users/DELL/Desktop/GlobalRetail_BI_360/data/generated_sources/Source_ERP_Ventes.csv"/**
																												 * End
																												 * field
																												 * tFileInputDelimited_1:FILENAME
																												 */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_1 = null;

				try {

					String[] rowtFileInputDelimited_1 = null;
					int currentLinetFileInputDelimited_1 = 0;
					int outputLinetFileInputDelimited_1 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_1 = 0;
							if (footer_value_tFileInputDelimited_1 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_1,
									fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
						} else {
							csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_1),
									fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
						}

						csvReadertFileInputDelimited_1.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_1[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_1[0] != '\r'))
							csvReadertFileInputDelimited_1.setLineEnd("" + rowSeparator_tFileInputDelimited_1[0]);

						csvReadertFileInputDelimited_1.setQuoteChar('"');

						csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());

						if (footer_tFileInputDelimited_1 > 0) {
							for (totalLinetFileInputDelimited_1 = 0; totalLinetFileInputDelimited_1 < 1; totalLinetFileInputDelimited_1++) {
								csvReadertFileInputDelimited_1.readNext();
							}
							csvReadertFileInputDelimited_1.setSkipEmptyRecords(false);
							while (csvReadertFileInputDelimited_1.readNext()) {

								totalLinetFileInputDelimited_1++;

							}
							int lastLineTemptFileInputDelimited_1 = totalLinetFileInputDelimited_1
									- footer_tFileInputDelimited_1 < 0 ? 0
											: totalLinetFileInputDelimited_1 - footer_tFileInputDelimited_1;
							if (lastLinetFileInputDelimited_1 > 0) {
								lastLinetFileInputDelimited_1 = lastLinetFileInputDelimited_1 < lastLineTemptFileInputDelimited_1
										? lastLinetFileInputDelimited_1
										: lastLineTemptFileInputDelimited_1;
							} else {
								lastLinetFileInputDelimited_1 = lastLineTemptFileInputDelimited_1;
							}

							csvReadertFileInputDelimited_1.close();
							if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_1,
										fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
							} else {
								csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_1),
										fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
							}
							csvReadertFileInputDelimited_1.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_1[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_1[0] != '\r'))
								csvReadertFileInputDelimited_1.setLineEnd("" + rowSeparator_tFileInputDelimited_1[0]);

							csvReadertFileInputDelimited_1.setQuoteChar('"');

							csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());

						}

						if (limittFileInputDelimited_1 != 0) {
							for (currentLinetFileInputDelimited_1 = 0; currentLinetFileInputDelimited_1 < 1; currentLinetFileInputDelimited_1++) {
								csvReadertFileInputDelimited_1.readNext();
							}
						}
						csvReadertFileInputDelimited_1.setSkipEmptyRecords(false);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					while (limittFileInputDelimited_1 != 0 && csvReadertFileInputDelimited_1 != null
							&& csvReadertFileInputDelimited_1.readNext()) {
						rowstate_tFileInputDelimited_1.reset();

						rowtFileInputDelimited_1 = csvReadertFileInputDelimited_1.getValues();

						currentLinetFileInputDelimited_1++;

						if (lastLinetFileInputDelimited_1 > -1
								&& currentLinetFileInputDelimited_1 > lastLinetFileInputDelimited_1) {
							break;
						}
						outputLinetFileInputDelimited_1++;
						if (limittFileInputDelimited_1 > 0
								&& outputLinetFileInputDelimited_1 > limittFileInputDelimited_1) {
							break;
						}

						row1 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row1 = new row1Struct();
						try {

							char fieldSeparator_tFileInputDelimited_1_ListType[] = null;
							// support passing value (property: Field Separator) by 'context.fs' or
							// 'globalMap.get("fs")'.
							if (((String) ";").length() > 0) {
								fieldSeparator_tFileInputDelimited_1_ListType = ((String) ";").toCharArray();
							} else {
								throw new IllegalArgumentException("Field Separator must be assigned a char.");
							}
							if (rowtFileInputDelimited_1.length == 1 && ("\015").equals(rowtFileInputDelimited_1[0])) {// empty
																														// line
																														// when
																														// row
																														// separator
																														// is
																														// '\n'

								row1.Row_ID = null;

								row1.Order_ID = null;

								row1.Order_Date = null;

								row1.Ship_Date = null;

								row1.Ship_Mode = null;

								row1.Customer_ID = null;

								row1.Customer_Name = null;

								row1.Segment = null;

								row1.City = null;

								row1.State = null;

								row1.Country = null;

								row1.Postal_Code = null;

								row1.Market = null;

								row1.Region = null;

								row1.Product_ID = null;

								row1.Category = null;

								row1.Sub_Category = null;

								row1.Product_Name = null;

								row1.Sales = null;

								row1.Quantity = null;

								row1.Discount = null;

								row1.Profit = null;

								row1.Shipping_Cost = null;

								row1.Order_Priority = null;

							} else {

								int columnIndexWithD_tFileInputDelimited_1 = 0; // Column Index

								columnIndexWithD_tFileInputDelimited_1 = 0;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Row_ID = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Row_ID", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Row_ID = null;

									}

								} else {

									row1.Row_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 1;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Order_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Order_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 2;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Order_Date = ParserUtils.parseTo_Date(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
													"dd-MM-yyyy");

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Order_Date", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Order_Date = null;

									}

								} else {

									row1.Order_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 3;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Ship_Date = ParserUtils.parseTo_Date(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
													"dd-MM-yyyy");

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Ship_Date", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Ship_Date = null;

									}

								} else {

									row1.Ship_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 4;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Ship_Mode = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Ship_Mode = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 5;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Customer_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Customer_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 6;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Customer_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Customer_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 7;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Segment = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Segment = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 8;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.City = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.City = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 9;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.State = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.State = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 10;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Country = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Country = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 11;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Postal_Code = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Postal_Code = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 12;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Market = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Market = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 13;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Region = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Region = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 14;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Product_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Product_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 15;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Category = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Category = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 16;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Sub_Category = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Sub_Category = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 17;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Product_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Product_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 18;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Sales = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Sales", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Sales = null;

									}

								} else {

									row1.Sales = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 19;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Quantity = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Quantity", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Quantity = null;

									}

								} else {

									row1.Quantity = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 20;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Discount = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Discount", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Discount = null;

									}

								} else {

									row1.Discount = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 21;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Profit = ParserUtils.parseTo_Double(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Profit", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Profit = null;

									}

								} else {

									row1.Profit = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 22;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Shipping_Cost = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Shipping_Cost", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Shipping_Cost = null;

									}

								} else {

									row1.Shipping_Cost = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 23;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Order_Priority = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Order_Priority = null;

								}

							}

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_1 = true;

							System.err.println(e.getMessage());
							row1 = null;

							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						}

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						tos_count_tFileInputDelimited_1++;

						/**
						 * [tFileInputDelimited_1 main ] stop
						 */

						/**
						 * [tFileInputDelimited_1 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_begin ] stop
						 */
// Start of branch "row1"
						if (row1 != null) {

							/**
							 * [tUniqRow_1 main ] start
							 */

							currentComponent = "tUniqRow_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row1"

								);
							}

							row2 = null;
							if (row1.City == null) {
								finder_tUniqRow_1.City = null;
							} else {
								finder_tUniqRow_1.City = row1.City.toLowerCase();
							}
							if (row1.State == null) {
								finder_tUniqRow_1.State = null;
							} else {
								finder_tUniqRow_1.State = row1.State.toLowerCase();
							}
							if (row1.Country == null) {
								finder_tUniqRow_1.Country = null;
							} else {
								finder_tUniqRow_1.Country = row1.Country.toLowerCase();
							}
							if (row1.Market == null) {
								finder_tUniqRow_1.Market = null;
							} else {
								finder_tUniqRow_1.Market = row1.Market.toLowerCase();
							}
							if (row1.Region == null) {
								finder_tUniqRow_1.Region = null;
							} else {
								finder_tUniqRow_1.Region = row1.Region.toLowerCase();
							}
							finder_tUniqRow_1.hashCodeDirty = true;
							if (!keystUniqRow_1.contains(finder_tUniqRow_1)) {
								KeyStruct_tUniqRow_1 new_tUniqRow_1 = new KeyStruct_tUniqRow_1();

								if (row1.City == null) {
									new_tUniqRow_1.City = null;
								} else {
									new_tUniqRow_1.City = row1.City.toLowerCase();
								}
								if (row1.State == null) {
									new_tUniqRow_1.State = null;
								} else {
									new_tUniqRow_1.State = row1.State.toLowerCase();
								}
								if (row1.Country == null) {
									new_tUniqRow_1.Country = null;
								} else {
									new_tUniqRow_1.Country = row1.Country.toLowerCase();
								}
								if (row1.Market == null) {
									new_tUniqRow_1.Market = null;
								} else {
									new_tUniqRow_1.Market = row1.Market.toLowerCase();
								}
								if (row1.Region == null) {
									new_tUniqRow_1.Region = null;
								} else {
									new_tUniqRow_1.Region = row1.Region.toLowerCase();
								}

								keystUniqRow_1.add(new_tUniqRow_1);
								if (row2 == null) {

									row2 = new row2Struct();
								}
								row2.Row_ID = row1.Row_ID;
								row2.Order_ID = row1.Order_ID;
								row2.Order_Date = row1.Order_Date;
								row2.Ship_Date = row1.Ship_Date;
								row2.Ship_Mode = row1.Ship_Mode;
								row2.Customer_ID = row1.Customer_ID;
								row2.Customer_Name = row1.Customer_Name;
								row2.Segment = row1.Segment;
								row2.City = row1.City;
								row2.State = row1.State;
								row2.Country = row1.Country;
								row2.Postal_Code = row1.Postal_Code;
								row2.Market = row1.Market;
								row2.Region = row1.Region;
								row2.Product_ID = row1.Product_ID;
								row2.Category = row1.Category;
								row2.Sub_Category = row1.Sub_Category;
								row2.Product_Name = row1.Product_Name;
								row2.Sales = row1.Sales;
								row2.Quantity = row1.Quantity;
								row2.Discount = row1.Discount;
								row2.Profit = row1.Profit;
								row2.Shipping_Cost = row1.Shipping_Cost;
								row2.Order_Priority = row1.Order_Priority;
								nb_uniques_tUniqRow_1++;
							} else {
								nb_duplicates_tUniqRow_1++;
							}

							tos_count_tUniqRow_1++;

							/**
							 * [tUniqRow_1 main ] stop
							 */

							/**
							 * [tUniqRow_1 process_data_begin ] start
							 */

							currentComponent = "tUniqRow_1";

							/**
							 * [tUniqRow_1 process_data_begin ] stop
							 */
// Start of branch "row2"
							if (row2 != null) {

								/**
								 * [tMap_1 main ] start
								 */

								currentComponent = "tMap_1";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row2"

									);
								}

								boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

								// ###############################
								// # Input tables (lookups)
								boolean rejectedInnerJoin_tMap_1 = false;
								boolean mainRowRejected_tMap_1 = false;

								// ###############################
								{ // start of Var scope

									// ###############################
									// # Vars tables

									Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
									// ###############################
									// # Output tables

									dim_location = null;

// # Output table : 'dim_location'
									dim_location_tmp.location_key = 0;
									dim_location_tmp.city = row2.City;
									dim_location_tmp.state = row2.State;
									dim_location_tmp.country = row2.Country;
									dim_location_tmp.region = row2.Region;
									dim_location_tmp.market = row2.Market;
									dim_location = dim_location_tmp;
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_1 = false;

								tos_count_tMap_1++;

								/**
								 * [tMap_1 main ] stop
								 */

								/**
								 * [tMap_1 process_data_begin ] start
								 */

								currentComponent = "tMap_1";

								/**
								 * [tMap_1 process_data_begin ] stop
								 */
// Start of branch "dim_location"
								if (dim_location != null) {

									/**
									 * [tDBOutput_1 main ] start
									 */

									currentComponent = "tDBOutput_1";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "dim_location"

										);
									}

									whetherReject_tDBOutput_1 = false;
									if (dim_location.city == null) {
										pstmt_tDBOutput_1.setNull(1, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(1, dim_location.city);
									}

									if (dim_location.state == null) {
										pstmt_tDBOutput_1.setNull(2, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(2, dim_location.state);
									}

									if (dim_location.country == null) {
										pstmt_tDBOutput_1.setNull(3, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(3, dim_location.country);
									}

									if (dim_location.region == null) {
										pstmt_tDBOutput_1.setNull(4, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(4, dim_location.region);
									}

									if (dim_location.market == null) {
										pstmt_tDBOutput_1.setNull(5, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_1.setString(5, dim_location.market);
									}

									pstmt_tDBOutput_1.addBatch();
									nb_line_tDBOutput_1++;

									batchSizeCounter_tDBOutput_1++;

									if ((batchSize_tDBOutput_1 > 0)
											&& (batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1)) {
										try {
											int countSum_tDBOutput_1 = 0;

											for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
												countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
														: countEach_tDBOutput_1);
											}
											rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

											insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

											batchSizeCounter_tDBOutput_1 = 0;
										} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
											globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
											java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),
													sqle_tDBOutput_1 = null;
											String errormessage_tDBOutput_1;
											if (ne_tDBOutput_1 != null) {
												// build new exception to provide the original cause
												sqle_tDBOutput_1 = new java.sql.SQLException(
														e_tDBOutput_1.getMessage() + "\ncaused by: "
																+ ne_tDBOutput_1.getMessage(),
														ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(),
														ne_tDBOutput_1);
												errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
											} else {
												errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
											}

											int countSum_tDBOutput_1 = 0;
											for (int countEach_tDBOutput_1 : e_tDBOutput_1.getUpdateCounts()) {
												countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
														: countEach_tDBOutput_1);
											}
											rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

											insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

											System.err.println(errormessage_tDBOutput_1);

										}
									}

									commitCounter_tDBOutput_1++;
									if (commitEvery_tDBOutput_1 <= commitCounter_tDBOutput_1) {
										if ((batchSize_tDBOutput_1 > 0) && (batchSizeCounter_tDBOutput_1 > 0)) {
											try {
												int countSum_tDBOutput_1 = 0;

												for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
													countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
															: countEach_tDBOutput_1);
												}
												rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

												insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

												batchSizeCounter_tDBOutput_1 = 0;
											} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
												globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
												java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),
														sqle_tDBOutput_1 = null;
												String errormessage_tDBOutput_1;
												if (ne_tDBOutput_1 != null) {
													// build new exception to provide the original cause
													sqle_tDBOutput_1 = new java.sql.SQLException(
															e_tDBOutput_1.getMessage() + "\ncaused by: "
																	+ ne_tDBOutput_1.getMessage(),
															ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(),
															ne_tDBOutput_1);
													errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
												} else {
													errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
												}

												int countSum_tDBOutput_1 = 0;
												for (int countEach_tDBOutput_1 : e_tDBOutput_1.getUpdateCounts()) {
													countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
															: countEach_tDBOutput_1);
												}
												rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

												insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

												System.err.println(errormessage_tDBOutput_1);

											}
										}
										if (rowsToCommitCount_tDBOutput_1 != 0) {

										}
										conn_tDBOutput_1.commit();
										if (rowsToCommitCount_tDBOutput_1 != 0) {

											rowsToCommitCount_tDBOutput_1 = 0;
										}
										commitCounter_tDBOutput_1 = 0;
									}

									tos_count_tDBOutput_1++;

									/**
									 * [tDBOutput_1 main ] stop
									 */

									/**
									 * [tDBOutput_1 process_data_begin ] start
									 */

									currentComponent = "tDBOutput_1";

									/**
									 * [tDBOutput_1 process_data_begin ] stop
									 */

									/**
									 * [tDBOutput_1 process_data_end ] start
									 */

									currentComponent = "tDBOutput_1";

									/**
									 * [tDBOutput_1 process_data_end ] stop
									 */

								} // End of branch "dim_location"

								/**
								 * [tMap_1 process_data_end ] start
								 */

								currentComponent = "tMap_1";

								/**
								 * [tMap_1 process_data_end ] stop
								 */

							} // End of branch "row2"

							/**
							 * [tUniqRow_1 process_data_end ] start
							 */

							currentComponent = "tUniqRow_1";

							/**
							 * [tUniqRow_1 process_data_end ] stop
							 */

						} // End of branch "row1"

						/**
						 * [tFileInputDelimited_1 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						nb_line_tFileInputDelimited_1++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_1 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_1 != null) {
							csvReadertFileInputDelimited_1.close();
						}
					}
					if (csvReadertFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE", nb_line_tFileInputDelimited_1);
					}

				}

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
				 */

				/**
				 * [tUniqRow_1 end ] start
				 */

				currentComponent = "tUniqRow_1";

				globalMap.put("tUniqRow_1_NB_UNIQUES", nb_uniques_tUniqRow_1);
				globalMap.put("tUniqRow_1_NB_DUPLICATES", nb_duplicates_tUniqRow_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row1");
				}

				ok_Hash.put("tUniqRow_1", true);
				end_Hash.put("tUniqRow_1", System.currentTimeMillis());

				/**
				 * [tUniqRow_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row2");
				}

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tDBOutput_1 end ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					int countSum_tDBOutput_1 = 0;
					if (pstmt_tDBOutput_1 != null && batchSizeCounter_tDBOutput_1 > 0) {

						for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
							countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
						}
						rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					}

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

				} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
					globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
					java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(), sqle_tDBOutput_1 = null;
					String errormessage_tDBOutput_1;
					if (ne_tDBOutput_1 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_1 = new java.sql.SQLException(
								e_tDBOutput_1.getMessage() + "\ncaused by: " + ne_tDBOutput_1.getMessage(),
								ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(), ne_tDBOutput_1);
						errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
					} else {
						errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
					}

					int countSum_tDBOutput_1 = 0;
					for (int countEach_tDBOutput_1 : e_tDBOutput_1.getUpdateCounts()) {
						countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
					rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					System.err.println(errormessage_tDBOutput_1);

				}

				if (pstmt_tDBOutput_1 != null) {

					pstmt_tDBOutput_1.close();
					resourceMap.remove("pstmt_tDBOutput_1");
				}
				resourceMap.put("statementClosed_tDBOutput_1", true);
				if (rowsToCommitCount_tDBOutput_1 != 0) {

				}
				conn_tDBOutput_1.commit();
				if (rowsToCommitCount_tDBOutput_1 != 0) {

					rowsToCommitCount_tDBOutput_1 = 0;
				}
				commitCounter_tDBOutput_1 = 0;

				conn_tDBOutput_1.close();

				resourceMap.put("finish_tDBOutput_1", true);

				nb_line_deleted_tDBOutput_1 = nb_line_deleted_tDBOutput_1 + deletedCount_tDBOutput_1;
				nb_line_update_tDBOutput_1 = nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
				nb_line_inserted_tDBOutput_1 = nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
				nb_line_rejected_tDBOutput_1 = nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;

				globalMap.put("tDBOutput_1_NB_LINE", nb_line_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_UPDATED", nb_line_update_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_DELETED", nb_line_deleted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "dim_location");
				}

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tFileInputDelimited_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk1", 0, "ok");
			}

			tFileInputDelimited_2Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_1 finally ] start
				 */

				currentComponent = "tFileInputDelimited_1";

				/**
				 * [tFileInputDelimited_1 finally ] stop
				 */

				/**
				 * [tUniqRow_1 finally ] start
				 */

				currentComponent = "tUniqRow_1";

				/**
				 * [tUniqRow_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tDBOutput_1 finally ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
						if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_1")) != null) {
							pstmtToClose_tDBOutput_1.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_1") == null) {
						java.sql.Connection ctn_tDBOutput_1 = null;
						if ((ctn_tDBOutput_1 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_1")) != null) {
							try {
								ctn_tDBOutput_1.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_1) {
								String errorMessage_tDBOutput_1 = "failed to close the connection in tDBOutput_1 :"
										+ sqlEx_tDBOutput_1.getMessage();
								System.err.println(errorMessage_tDBOutput_1);
							}
						}
					}
				}

				/**
				 * [tDBOutput_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 1);
	}

	public static class Out_LocationStruct implements routines.system.IPersistableRow<Out_LocationStruct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int product_key;

		public int getProduct_key() {
			return this.product_key;
		}

		public String product_id_source;

		public String getProduct_id_source() {
			return this.product_id_source;
		}

		public String category;

		public String getCategory() {
			return this.category;
		}

		public String sub_category;

		public String getSub_category() {
			return this.sub_category;
		}

		public String product_name;

		public String getProduct_name() {
			return this.product_name;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.product_key;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Out_LocationStruct other = (Out_LocationStruct) obj;

			if (this.product_key != other.product_key)
				return false;

			return true;
		}

		public void copyDataTo(Out_LocationStruct other) {

			other.product_key = this.product_key;
			other.product_id_source = this.product_id_source;
			other.category = this.category;
			other.sub_category = this.sub_category;
			other.product_name = this.product_name;

		}

		public void copyKeysDataTo(Out_LocationStruct other) {

			other.product_key = this.product_key;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.product_key = dis.readInt();

					this.product_id_source = readString(dis);

					this.category = readString(dis);

					this.sub_category = readString(dis);

					this.product_name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.product_key = dis.readInt();

					this.product_id_source = readString(dis);

					this.category = readString(dis);

					this.sub_category = readString(dis);

					this.product_name = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.product_key);

				// String

				writeString(this.product_id_source, dos);

				// String

				writeString(this.category, dos);

				// String

				writeString(this.sub_category, dos);

				// String

				writeString(this.product_name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.product_key);

				// String

				writeString(this.product_id_source, dos);

				// String

				writeString(this.category, dos);

				// String

				writeString(this.sub_category, dos);

				// String

				writeString(this.product_name, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("product_key=" + String.valueOf(product_key));
			sb.append(",product_id_source=" + product_id_source);
			sb.append(",category=" + category);
			sb.append(",sub_category=" + sub_category);
			sb.append(",product_name=" + product_name);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(Out_LocationStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.product_key, other.product_key);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row4Struct implements routines.system.IPersistableRow<row4Struct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];

		public Integer Row_ID;

		public Integer getRow_ID() {
			return this.Row_ID;
		}

		public String Order_ID;

		public String getOrder_ID() {
			return this.Order_ID;
		}

		public java.util.Date Order_Date;

		public java.util.Date getOrder_Date() {
			return this.Order_Date;
		}

		public java.util.Date Ship_Date;

		public java.util.Date getShip_Date() {
			return this.Ship_Date;
		}

		public String Ship_Mode;

		public String getShip_Mode() {
			return this.Ship_Mode;
		}

		public String Customer_ID;

		public String getCustomer_ID() {
			return this.Customer_ID;
		}

		public String Customer_Name;

		public String getCustomer_Name() {
			return this.Customer_Name;
		}

		public String Segment;

		public String getSegment() {
			return this.Segment;
		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public String State;

		public String getState() {
			return this.State;
		}

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Postal_Code;

		public String getPostal_Code() {
			return this.Postal_Code;
		}

		public String Market;

		public String getMarket() {
			return this.Market;
		}

		public String Region;

		public String getRegion() {
			return this.Region;
		}

		public String Product_ID;

		public String getProduct_ID() {
			return this.Product_ID;
		}

		public String Category;

		public String getCategory() {
			return this.Category;
		}

		public String Sub_Category;

		public String getSub_Category() {
			return this.Sub_Category;
		}

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Float Sales;

		public Float getSales() {
			return this.Sales;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public Float Discount;

		public Float getDiscount() {
			return this.Discount;
		}

		public Double Profit;

		public Double getProfit() {
			return this.Profit;
		}

		public Float Shipping_Cost;

		public Float getShipping_Cost() {
			return this.Shipping_Cost;
		}

		public String Order_Priority;

		public String getOrder_Priority() {
			return this.Order_Priority;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Row_ID=" + String.valueOf(Row_ID));
			sb.append(",Order_ID=" + Order_ID);
			sb.append(",Order_Date=" + String.valueOf(Order_Date));
			sb.append(",Ship_Date=" + String.valueOf(Ship_Date));
			sb.append(",Ship_Mode=" + Ship_Mode);
			sb.append(",Customer_ID=" + Customer_ID);
			sb.append(",Customer_Name=" + Customer_Name);
			sb.append(",Segment=" + Segment);
			sb.append(",City=" + City);
			sb.append(",State=" + State);
			sb.append(",Country=" + Country);
			sb.append(",Postal_Code=" + Postal_Code);
			sb.append(",Market=" + Market);
			sb.append(",Region=" + Region);
			sb.append(",Product_ID=" + Product_ID);
			sb.append(",Category=" + Category);
			sb.append(",Sub_Category=" + Sub_Category);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Sales=" + String.valueOf(Sales));
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",Discount=" + String.valueOf(Discount));
			sb.append(",Profit=" + String.valueOf(Profit));
			sb.append(",Shipping_Cost=" + String.valueOf(Shipping_Cost));
			sb.append(",Order_Priority=" + Order_Priority);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer Row_ID;

		public Integer getRow_ID() {
			return this.Row_ID;
		}

		public String Order_ID;

		public String getOrder_ID() {
			return this.Order_ID;
		}

		public java.util.Date Order_Date;

		public java.util.Date getOrder_Date() {
			return this.Order_Date;
		}

		public java.util.Date Ship_Date;

		public java.util.Date getShip_Date() {
			return this.Ship_Date;
		}

		public String Ship_Mode;

		public String getShip_Mode() {
			return this.Ship_Mode;
		}

		public String Customer_ID;

		public String getCustomer_ID() {
			return this.Customer_ID;
		}

		public String Customer_Name;

		public String getCustomer_Name() {
			return this.Customer_Name;
		}

		public String Segment;

		public String getSegment() {
			return this.Segment;
		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public String State;

		public String getState() {
			return this.State;
		}

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Postal_Code;

		public String getPostal_Code() {
			return this.Postal_Code;
		}

		public String Market;

		public String getMarket() {
			return this.Market;
		}

		public String Region;

		public String getRegion() {
			return this.Region;
		}

		public String Product_ID;

		public String getProduct_ID() {
			return this.Product_ID;
		}

		public String Category;

		public String getCategory() {
			return this.Category;
		}

		public String Sub_Category;

		public String getSub_Category() {
			return this.Sub_Category;
		}

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Float Sales;

		public Float getSales() {
			return this.Sales;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public Float Discount;

		public Float getDiscount() {
			return this.Discount;
		}

		public Double Profit;

		public Double getProfit() {
			return this.Profit;
		}

		public Float Shipping_Cost;

		public Float getShipping_Cost() {
			return this.Shipping_Cost;
		}

		public String Order_Priority;

		public String getOrder_Priority() {
			return this.Order_Priority;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Row_ID == null) ? 0 : this.Row_ID.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row3Struct other = (row3Struct) obj;

			if (this.Row_ID == null) {
				if (other.Row_ID != null)
					return false;

			} else if (!this.Row_ID.equals(other.Row_ID))

				return false;

			return true;
		}

		public void copyDataTo(row3Struct other) {

			other.Row_ID = this.Row_ID;
			other.Order_ID = this.Order_ID;
			other.Order_Date = this.Order_Date;
			other.Ship_Date = this.Ship_Date;
			other.Ship_Mode = this.Ship_Mode;
			other.Customer_ID = this.Customer_ID;
			other.Customer_Name = this.Customer_Name;
			other.Segment = this.Segment;
			other.City = this.City;
			other.State = this.State;
			other.Country = this.Country;
			other.Postal_Code = this.Postal_Code;
			other.Market = this.Market;
			other.Region = this.Region;
			other.Product_ID = this.Product_ID;
			other.Category = this.Category;
			other.Sub_Category = this.Sub_Category;
			other.Product_Name = this.Product_Name;
			other.Sales = this.Sales;
			other.Quantity = this.Quantity;
			other.Discount = this.Discount;
			other.Profit = this.Profit;
			other.Shipping_Cost = this.Shipping_Cost;
			other.Order_Priority = this.Order_Priority;

		}

		public void copyKeysDataTo(row3Struct other) {

			other.Row_ID = this.Row_ID;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Row_ID=" + String.valueOf(Row_ID));
			sb.append(",Order_ID=" + Order_ID);
			sb.append(",Order_Date=" + String.valueOf(Order_Date));
			sb.append(",Ship_Date=" + String.valueOf(Ship_Date));
			sb.append(",Ship_Mode=" + Ship_Mode);
			sb.append(",Customer_ID=" + Customer_ID);
			sb.append(",Customer_Name=" + Customer_Name);
			sb.append(",Segment=" + Segment);
			sb.append(",City=" + City);
			sb.append(",State=" + State);
			sb.append(",Country=" + Country);
			sb.append(",Postal_Code=" + Postal_Code);
			sb.append(",Market=" + Market);
			sb.append(",Region=" + Region);
			sb.append(",Product_ID=" + Product_ID);
			sb.append(",Category=" + Category);
			sb.append(",Sub_Category=" + Sub_Category);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Sales=" + String.valueOf(Sales));
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",Discount=" + String.valueOf(Discount));
			sb.append(",Profit=" + String.valueOf(Profit));
			sb.append(",Shipping_Cost=" + String.valueOf(Shipping_Cost));
			sb.append(",Order_Priority=" + Order_Priority);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Row_ID, other.Row_ID);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row3Struct row3 = new row3Struct();
				row4Struct row4 = new row4Struct();
				Out_LocationStruct Out_Location = new Out_LocationStruct();

				/**
				 * [tDBOutput_2 begin ] start
				 */

				ok_Hash.put("tDBOutput_2", false);
				start_Hash.put("tDBOutput_2", System.currentTimeMillis());

				currentComponent = "tDBOutput_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "Out_Location");
				}

				int tos_count_tDBOutput_2 = 0;

				String dbschema_tDBOutput_2 = null;
				dbschema_tDBOutput_2 = "public";

				String tableName_tDBOutput_2 = null;
				if (dbschema_tDBOutput_2 == null || dbschema_tDBOutput_2.trim().length() == 0) {
					tableName_tDBOutput_2 = ("dim_product");
				} else {
					tableName_tDBOutput_2 = dbschema_tDBOutput_2 + "\".\"" + ("dim_product");
				}

				int nb_line_tDBOutput_2 = 0;
				int nb_line_update_tDBOutput_2 = 0;
				int nb_line_inserted_tDBOutput_2 = 0;
				int nb_line_deleted_tDBOutput_2 = 0;
				int nb_line_rejected_tDBOutput_2 = 0;

				int deletedCount_tDBOutput_2 = 0;
				int updatedCount_tDBOutput_2 = 0;
				int insertedCount_tDBOutput_2 = 0;
				int rowsToCommitCount_tDBOutput_2 = 0;
				int rejectedCount_tDBOutput_2 = 0;

				boolean whetherReject_tDBOutput_2 = false;

				java.sql.Connection conn_tDBOutput_2 = null;
				String dbUser_tDBOutput_2 = null;

				java.lang.Class.forName("org.postgresql.Driver");

				String url_tDBOutput_2 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "GlobalRetailDW";
				dbUser_tDBOutput_2 = "postgres";

				final String decryptedPassword_tDBOutput_2 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:rHPWeNWKBL2FNgAY4Wbt8spc17zBXR1ORqbyHsrEQhwvwtaDIQ==");

				String dbPwd_tDBOutput_2 = decryptedPassword_tDBOutput_2;

				conn_tDBOutput_2 = java.sql.DriverManager.getConnection(url_tDBOutput_2, dbUser_tDBOutput_2,
						dbPwd_tDBOutput_2);

				resourceMap.put("conn_tDBOutput_2", conn_tDBOutput_2);
				conn_tDBOutput_2.setAutoCommit(false);
				int commitEvery_tDBOutput_2 = 10000;
				int commitCounter_tDBOutput_2 = 0;

				int batchSize_tDBOutput_2 = 10000;
				int batchSizeCounter_tDBOutput_2 = 0;

				int count_tDBOutput_2 = 0;
				String insert_tDBOutput_2 = "INSERT INTO \"" + tableName_tDBOutput_2
						+ "\" (\"product_id_source\",\"category\",\"sub_category\",\"product_name\") VALUES (?,?,?,?)";

				java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(insert_tDBOutput_2);
				resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);

				/**
				 * [tDBOutput_2 begin ] stop
				 */

				/**
				 * [tMap_2 begin ] start
				 */

				ok_Hash.put("tMap_2", false);
				start_Hash.put("tMap_2", System.currentTimeMillis());

				currentComponent = "tMap_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row4");
				}

				int tos_count_tMap_2 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_2__Struct {
				}
				Var__tMap_2__Struct Var__tMap_2 = new Var__tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
				Out_LocationStruct Out_Location_tmp = new Out_LocationStruct();
// ###############################

				/**
				 * [tMap_2 begin ] stop
				 */

				/**
				 * [tUniqRow_2 begin ] start
				 */

				ok_Hash.put("tUniqRow_2", false);
				start_Hash.put("tUniqRow_2", System.currentTimeMillis());

				currentComponent = "tUniqRow_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row3");
				}

				int tos_count_tUniqRow_2 = 0;

				class KeyStruct_tUniqRow_2 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					String Product_ID;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.Product_ID == null) ? 0 : this.Product_ID.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_2 other = (KeyStruct_tUniqRow_2) obj;

						if (this.Product_ID == null) {
							if (other.Product_ID != null)
								return false;

						} else if (!this.Product_ID.equals(other.Product_ID))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_2 = 0;
				int nb_duplicates_tUniqRow_2 = 0;
				KeyStruct_tUniqRow_2 finder_tUniqRow_2 = new KeyStruct_tUniqRow_2();
				java.util.Set<KeyStruct_tUniqRow_2> keystUniqRow_2 = new java.util.HashSet<KeyStruct_tUniqRow_2>();

				/**
				 * [tUniqRow_2 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_2 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_2", false);
				start_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_2";

				int tos_count_tFileInputDelimited_2 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_2 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_2 = 0;
				int footer_tFileInputDelimited_2 = 0;
				int totalLinetFileInputDelimited_2 = 0;
				int limittFileInputDelimited_2 = -1;
				int lastLinetFileInputDelimited_2 = -1;

				char fieldSeparator_tFileInputDelimited_2[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ";").length() > 0) {
					fieldSeparator_tFileInputDelimited_2 = ((String) ";").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_2[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_2 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_2 = /** Start field tFileInputDelimited_2:FILENAME */
						"C:/Users/DELL/Desktop/GlobalRetail_BI_360/data/generated_sources/Source_ERP_Ventes.csv"/**
																												 * End
																												 * field
																												 * tFileInputDelimited_2:FILENAME
																												 */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_2 = null;

				try {

					String[] rowtFileInputDelimited_2 = null;
					int currentLinetFileInputDelimited_2 = 0;
					int outputLinetFileInputDelimited_2 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_2 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_2 = 0;
							if (footer_value_tFileInputDelimited_2 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_2,
									fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
						} else {
							csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_2),
									fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
						}

						csvReadertFileInputDelimited_2.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_2[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_2[0] != '\r'))
							csvReadertFileInputDelimited_2.setLineEnd("" + rowSeparator_tFileInputDelimited_2[0]);

						csvReadertFileInputDelimited_2.setQuoteChar('"');

						csvReadertFileInputDelimited_2.setEscapeChar(csvReadertFileInputDelimited_2.getQuoteChar());

						if (footer_tFileInputDelimited_2 > 0) {
							for (totalLinetFileInputDelimited_2 = 0; totalLinetFileInputDelimited_2 < 1; totalLinetFileInputDelimited_2++) {
								csvReadertFileInputDelimited_2.readNext();
							}
							csvReadertFileInputDelimited_2.setSkipEmptyRecords(false);
							while (csvReadertFileInputDelimited_2.readNext()) {

								totalLinetFileInputDelimited_2++;

							}
							int lastLineTemptFileInputDelimited_2 = totalLinetFileInputDelimited_2
									- footer_tFileInputDelimited_2 < 0 ? 0
											: totalLinetFileInputDelimited_2 - footer_tFileInputDelimited_2;
							if (lastLinetFileInputDelimited_2 > 0) {
								lastLinetFileInputDelimited_2 = lastLinetFileInputDelimited_2 < lastLineTemptFileInputDelimited_2
										? lastLinetFileInputDelimited_2
										: lastLineTemptFileInputDelimited_2;
							} else {
								lastLinetFileInputDelimited_2 = lastLineTemptFileInputDelimited_2;
							}

							csvReadertFileInputDelimited_2.close();
							if (filename_tFileInputDelimited_2 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_2,
										fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
							} else {
								csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_2),
										fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
							}
							csvReadertFileInputDelimited_2.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_2[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_2[0] != '\r'))
								csvReadertFileInputDelimited_2.setLineEnd("" + rowSeparator_tFileInputDelimited_2[0]);

							csvReadertFileInputDelimited_2.setQuoteChar('"');

							csvReadertFileInputDelimited_2.setEscapeChar(csvReadertFileInputDelimited_2.getQuoteChar());

						}

						if (limittFileInputDelimited_2 != 0) {
							for (currentLinetFileInputDelimited_2 = 0; currentLinetFileInputDelimited_2 < 1; currentLinetFileInputDelimited_2++) {
								csvReadertFileInputDelimited_2.readNext();
							}
						}
						csvReadertFileInputDelimited_2.setSkipEmptyRecords(false);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					while (limittFileInputDelimited_2 != 0 && csvReadertFileInputDelimited_2 != null
							&& csvReadertFileInputDelimited_2.readNext()) {
						rowstate_tFileInputDelimited_2.reset();

						rowtFileInputDelimited_2 = csvReadertFileInputDelimited_2.getValues();

						currentLinetFileInputDelimited_2++;

						if (lastLinetFileInputDelimited_2 > -1
								&& currentLinetFileInputDelimited_2 > lastLinetFileInputDelimited_2) {
							break;
						}
						outputLinetFileInputDelimited_2++;
						if (limittFileInputDelimited_2 > 0
								&& outputLinetFileInputDelimited_2 > limittFileInputDelimited_2) {
							break;
						}

						row3 = null;

						boolean whetherReject_tFileInputDelimited_2 = false;
						row3 = new row3Struct();
						try {

							char fieldSeparator_tFileInputDelimited_2_ListType[] = null;
							// support passing value (property: Field Separator) by 'context.fs' or
							// 'globalMap.get("fs")'.
							if (((String) ";").length() > 0) {
								fieldSeparator_tFileInputDelimited_2_ListType = ((String) ";").toCharArray();
							} else {
								throw new IllegalArgumentException("Field Separator must be assigned a char.");
							}
							if (rowtFileInputDelimited_2.length == 1 && ("\015").equals(rowtFileInputDelimited_2[0])) {// empty
																														// line
																														// when
																														// row
																														// separator
																														// is
																														// '\n'

								row3.Row_ID = null;

								row3.Order_ID = null;

								row3.Order_Date = null;

								row3.Ship_Date = null;

								row3.Ship_Mode = null;

								row3.Customer_ID = null;

								row3.Customer_Name = null;

								row3.Segment = null;

								row3.City = null;

								row3.State = null;

								row3.Country = null;

								row3.Postal_Code = null;

								row3.Market = null;

								row3.Region = null;

								row3.Product_ID = null;

								row3.Category = null;

								row3.Sub_Category = null;

								row3.Product_Name = null;

								row3.Sales = null;

								row3.Quantity = null;

								row3.Discount = null;

								row3.Profit = null;

								row3.Shipping_Cost = null;

								row3.Order_Priority = null;

							} else {

								int columnIndexWithD_tFileInputDelimited_2 = 0; // Column Index

								columnIndexWithD_tFileInputDelimited_2 = 0;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row3.Row_ID = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Row_ID", "row3",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row3.Row_ID = null;

									}

								} else {

									row3.Row_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 1;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Order_ID = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Order_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 2;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row3.Order_Date = ParserUtils.parseTo_Date(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
													"dd-MM-yyyy");

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Order_Date", "row3",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row3.Order_Date = null;

									}

								} else {

									row3.Order_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 3;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row3.Ship_Date = ParserUtils.parseTo_Date(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
													"dd-MM-yyyy");

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Ship_Date", "row3",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row3.Ship_Date = null;

									}

								} else {

									row3.Ship_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 4;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Ship_Mode = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Ship_Mode = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 5;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Customer_ID = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Customer_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 6;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Customer_Name = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Customer_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 7;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Segment = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Segment = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 8;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.City = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.City = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 9;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.State = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.State = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 10;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Country = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Country = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 11;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Postal_Code = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Postal_Code = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 12;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Market = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Market = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 13;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Region = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Region = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 14;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Product_ID = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Product_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 15;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Category = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Category = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 16;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Sub_Category = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Sub_Category = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 17;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Product_Name = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Product_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 18;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row3.Sales = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Sales", "row3",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row3.Sales = null;

									}

								} else {

									row3.Sales = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 19;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row3.Quantity = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Quantity", "row3",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row3.Quantity = null;

									}

								} else {

									row3.Quantity = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 20;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row3.Discount = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Discount", "row3",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row3.Discount = null;

									}

								} else {

									row3.Discount = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 21;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row3.Profit = ParserUtils.parseTo_Double(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Profit", "row3",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row3.Profit = null;

									}

								} else {

									row3.Profit = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 22;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row3.Shipping_Cost = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Shipping_Cost", "row3",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row3.Shipping_Cost = null;

									}

								} else {

									row3.Shipping_Cost = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 23;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row3.Order_Priority = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row3.Order_Priority = null;

								}

							}

							if (rowstate_tFileInputDelimited_2.getException() != null) {
								throw rowstate_tFileInputDelimited_2.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_2 = true;

							System.err.println(e.getMessage());
							row3 = null;

							globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE", e.getMessage());

						}

						/**
						 * [tFileInputDelimited_2 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_2 main ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						tos_count_tFileInputDelimited_2++;

						/**
						 * [tFileInputDelimited_2 main ] stop
						 */

						/**
						 * [tFileInputDelimited_2 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						/**
						 * [tFileInputDelimited_2 process_data_begin ] stop
						 */
// Start of branch "row3"
						if (row3 != null) {

							/**
							 * [tUniqRow_2 main ] start
							 */

							currentComponent = "tUniqRow_2";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row3"

								);
							}

							row4 = null;
							if (row3.Product_ID == null) {
								finder_tUniqRow_2.Product_ID = null;
							} else {
								finder_tUniqRow_2.Product_ID = row3.Product_ID.toLowerCase();
							}
							finder_tUniqRow_2.hashCodeDirty = true;
							if (!keystUniqRow_2.contains(finder_tUniqRow_2)) {
								KeyStruct_tUniqRow_2 new_tUniqRow_2 = new KeyStruct_tUniqRow_2();

								if (row3.Product_ID == null) {
									new_tUniqRow_2.Product_ID = null;
								} else {
									new_tUniqRow_2.Product_ID = row3.Product_ID.toLowerCase();
								}

								keystUniqRow_2.add(new_tUniqRow_2);
								if (row4 == null) {

									row4 = new row4Struct();
								}
								row4.Row_ID = row3.Row_ID;
								row4.Order_ID = row3.Order_ID;
								row4.Order_Date = row3.Order_Date;
								row4.Ship_Date = row3.Ship_Date;
								row4.Ship_Mode = row3.Ship_Mode;
								row4.Customer_ID = row3.Customer_ID;
								row4.Customer_Name = row3.Customer_Name;
								row4.Segment = row3.Segment;
								row4.City = row3.City;
								row4.State = row3.State;
								row4.Country = row3.Country;
								row4.Postal_Code = row3.Postal_Code;
								row4.Market = row3.Market;
								row4.Region = row3.Region;
								row4.Product_ID = row3.Product_ID;
								row4.Category = row3.Category;
								row4.Sub_Category = row3.Sub_Category;
								row4.Product_Name = row3.Product_Name;
								row4.Sales = row3.Sales;
								row4.Quantity = row3.Quantity;
								row4.Discount = row3.Discount;
								row4.Profit = row3.Profit;
								row4.Shipping_Cost = row3.Shipping_Cost;
								row4.Order_Priority = row3.Order_Priority;
								nb_uniques_tUniqRow_2++;
							} else {
								nb_duplicates_tUniqRow_2++;
							}

							tos_count_tUniqRow_2++;

							/**
							 * [tUniqRow_2 main ] stop
							 */

							/**
							 * [tUniqRow_2 process_data_begin ] start
							 */

							currentComponent = "tUniqRow_2";

							/**
							 * [tUniqRow_2 process_data_begin ] stop
							 */
// Start of branch "row4"
							if (row4 != null) {

								/**
								 * [tMap_2 main ] start
								 */

								currentComponent = "tMap_2";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row4"

									);
								}

								boolean hasCasePrimitiveKeyWithNull_tMap_2 = false;

								// ###############################
								// # Input tables (lookups)
								boolean rejectedInnerJoin_tMap_2 = false;
								boolean mainRowRejected_tMap_2 = false;

								// ###############################
								{ // start of Var scope

									// ###############################
									// # Vars tables

									Var__tMap_2__Struct Var = Var__tMap_2;// ###############################
									// ###############################
									// # Output tables

									Out_Location = null;

// # Output table : 'Out_Location'
									Out_Location_tmp.product_key = 0;
									Out_Location_tmp.product_id_source = row4.Product_ID;
									Out_Location_tmp.category = row4.Category;
									Out_Location_tmp.sub_category = row4.Sub_Category;
									Out_Location_tmp.product_name = row4.Product_Name;
									Out_Location = Out_Location_tmp;
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_2 = false;

								tos_count_tMap_2++;

								/**
								 * [tMap_2 main ] stop
								 */

								/**
								 * [tMap_2 process_data_begin ] start
								 */

								currentComponent = "tMap_2";

								/**
								 * [tMap_2 process_data_begin ] stop
								 */
// Start of branch "Out_Location"
								if (Out_Location != null) {

									/**
									 * [tDBOutput_2 main ] start
									 */

									currentComponent = "tDBOutput_2";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "Out_Location"

										);
									}

									whetherReject_tDBOutput_2 = false;
									if (Out_Location.product_id_source == null) {
										pstmt_tDBOutput_2.setNull(1, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_2.setString(1, Out_Location.product_id_source);
									}

									if (Out_Location.category == null) {
										pstmt_tDBOutput_2.setNull(2, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_2.setString(2, Out_Location.category);
									}

									if (Out_Location.sub_category == null) {
										pstmt_tDBOutput_2.setNull(3, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_2.setString(3, Out_Location.sub_category);
									}

									if (Out_Location.product_name == null) {
										pstmt_tDBOutput_2.setNull(4, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_2.setString(4, Out_Location.product_name);
									}

									pstmt_tDBOutput_2.addBatch();
									nb_line_tDBOutput_2++;

									batchSizeCounter_tDBOutput_2++;

									if ((batchSize_tDBOutput_2 > 0)
											&& (batchSize_tDBOutput_2 <= batchSizeCounter_tDBOutput_2)) {
										try {
											int countSum_tDBOutput_2 = 0;

											for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
												countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
														: countEach_tDBOutput_2);
											}
											rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

											insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

											batchSizeCounter_tDBOutput_2 = 0;
										} catch (java.sql.BatchUpdateException e_tDBOutput_2) {
											globalMap.put("tDBOutput_2_ERROR_MESSAGE", e_tDBOutput_2.getMessage());
											java.sql.SQLException ne_tDBOutput_2 = e_tDBOutput_2.getNextException(),
													sqle_tDBOutput_2 = null;
											String errormessage_tDBOutput_2;
											if (ne_tDBOutput_2 != null) {
												// build new exception to provide the original cause
												sqle_tDBOutput_2 = new java.sql.SQLException(
														e_tDBOutput_2.getMessage() + "\ncaused by: "
																+ ne_tDBOutput_2.getMessage(),
														ne_tDBOutput_2.getSQLState(), ne_tDBOutput_2.getErrorCode(),
														ne_tDBOutput_2);
												errormessage_tDBOutput_2 = sqle_tDBOutput_2.getMessage();
											} else {
												errormessage_tDBOutput_2 = e_tDBOutput_2.getMessage();
											}

											int countSum_tDBOutput_2 = 0;
											for (int countEach_tDBOutput_2 : e_tDBOutput_2.getUpdateCounts()) {
												countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
														: countEach_tDBOutput_2);
											}
											rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

											insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

											System.err.println(errormessage_tDBOutput_2);

										}
									}

									commitCounter_tDBOutput_2++;
									if (commitEvery_tDBOutput_2 <= commitCounter_tDBOutput_2) {
										if ((batchSize_tDBOutput_2 > 0) && (batchSizeCounter_tDBOutput_2 > 0)) {
											try {
												int countSum_tDBOutput_2 = 0;

												for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
													countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
															: countEach_tDBOutput_2);
												}
												rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

												insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

												batchSizeCounter_tDBOutput_2 = 0;
											} catch (java.sql.BatchUpdateException e_tDBOutput_2) {
												globalMap.put("tDBOutput_2_ERROR_MESSAGE", e_tDBOutput_2.getMessage());
												java.sql.SQLException ne_tDBOutput_2 = e_tDBOutput_2.getNextException(),
														sqle_tDBOutput_2 = null;
												String errormessage_tDBOutput_2;
												if (ne_tDBOutput_2 != null) {
													// build new exception to provide the original cause
													sqle_tDBOutput_2 = new java.sql.SQLException(
															e_tDBOutput_2.getMessage() + "\ncaused by: "
																	+ ne_tDBOutput_2.getMessage(),
															ne_tDBOutput_2.getSQLState(), ne_tDBOutput_2.getErrorCode(),
															ne_tDBOutput_2);
													errormessage_tDBOutput_2 = sqle_tDBOutput_2.getMessage();
												} else {
													errormessage_tDBOutput_2 = e_tDBOutput_2.getMessage();
												}

												int countSum_tDBOutput_2 = 0;
												for (int countEach_tDBOutput_2 : e_tDBOutput_2.getUpdateCounts()) {
													countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
															: countEach_tDBOutput_2);
												}
												rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

												insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

												System.err.println(errormessage_tDBOutput_2);

											}
										}
										if (rowsToCommitCount_tDBOutput_2 != 0) {

										}
										conn_tDBOutput_2.commit();
										if (rowsToCommitCount_tDBOutput_2 != 0) {

											rowsToCommitCount_tDBOutput_2 = 0;
										}
										commitCounter_tDBOutput_2 = 0;
									}

									tos_count_tDBOutput_2++;

									/**
									 * [tDBOutput_2 main ] stop
									 */

									/**
									 * [tDBOutput_2 process_data_begin ] start
									 */

									currentComponent = "tDBOutput_2";

									/**
									 * [tDBOutput_2 process_data_begin ] stop
									 */

									/**
									 * [tDBOutput_2 process_data_end ] start
									 */

									currentComponent = "tDBOutput_2";

									/**
									 * [tDBOutput_2 process_data_end ] stop
									 */

								} // End of branch "Out_Location"

								/**
								 * [tMap_2 process_data_end ] start
								 */

								currentComponent = "tMap_2";

								/**
								 * [tMap_2 process_data_end ] stop
								 */

							} // End of branch "row4"

							/**
							 * [tUniqRow_2 process_data_end ] start
							 */

							currentComponent = "tUniqRow_2";

							/**
							 * [tUniqRow_2 process_data_end ] stop
							 */

						} // End of branch "row3"

						/**
						 * [tFileInputDelimited_2 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						/**
						 * [tFileInputDelimited_2 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_2 end ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						nb_line_tFileInputDelimited_2++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_2 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_2 != null) {
							csvReadertFileInputDelimited_2.close();
						}
					}
					if (csvReadertFileInputDelimited_2 != null) {
						globalMap.put("tFileInputDelimited_2_NB_LINE", nb_line_tFileInputDelimited_2);
					}

				}

				ok_Hash.put("tFileInputDelimited_2", true);
				end_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_2 end ] stop
				 */

				/**
				 * [tUniqRow_2 end ] start
				 */

				currentComponent = "tUniqRow_2";

				globalMap.put("tUniqRow_2_NB_UNIQUES", nb_uniques_tUniqRow_2);
				globalMap.put("tUniqRow_2_NB_DUPLICATES", nb_duplicates_tUniqRow_2);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row3");
				}

				ok_Hash.put("tUniqRow_2", true);
				end_Hash.put("tUniqRow_2", System.currentTimeMillis());

				/**
				 * [tUniqRow_2 end ] stop
				 */

				/**
				 * [tMap_2 end ] start
				 */

				currentComponent = "tMap_2";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row4");
				}

				ok_Hash.put("tMap_2", true);
				end_Hash.put("tMap_2", System.currentTimeMillis());

				/**
				 * [tMap_2 end ] stop
				 */

				/**
				 * [tDBOutput_2 end ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					int countSum_tDBOutput_2 = 0;
					if (pstmt_tDBOutput_2 != null && batchSizeCounter_tDBOutput_2 > 0) {

						for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
							countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
						}
						rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					}

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

				} catch (java.sql.BatchUpdateException e_tDBOutput_2) {
					globalMap.put("tDBOutput_2_ERROR_MESSAGE", e_tDBOutput_2.getMessage());
					java.sql.SQLException ne_tDBOutput_2 = e_tDBOutput_2.getNextException(), sqle_tDBOutput_2 = null;
					String errormessage_tDBOutput_2;
					if (ne_tDBOutput_2 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_2 = new java.sql.SQLException(
								e_tDBOutput_2.getMessage() + "\ncaused by: " + ne_tDBOutput_2.getMessage(),
								ne_tDBOutput_2.getSQLState(), ne_tDBOutput_2.getErrorCode(), ne_tDBOutput_2);
						errormessage_tDBOutput_2 = sqle_tDBOutput_2.getMessage();
					} else {
						errormessage_tDBOutput_2 = e_tDBOutput_2.getMessage();
					}

					int countSum_tDBOutput_2 = 0;
					for (int countEach_tDBOutput_2 : e_tDBOutput_2.getUpdateCounts()) {
						countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
					}
					rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

					System.err.println(errormessage_tDBOutput_2);

				}

				if (pstmt_tDBOutput_2 != null) {

					pstmt_tDBOutput_2.close();
					resourceMap.remove("pstmt_tDBOutput_2");
				}
				resourceMap.put("statementClosed_tDBOutput_2", true);
				if (rowsToCommitCount_tDBOutput_2 != 0) {

				}
				conn_tDBOutput_2.commit();
				if (rowsToCommitCount_tDBOutput_2 != 0) {

					rowsToCommitCount_tDBOutput_2 = 0;
				}
				commitCounter_tDBOutput_2 = 0;

				conn_tDBOutput_2.close();

				resourceMap.put("finish_tDBOutput_2", true);

				nb_line_deleted_tDBOutput_2 = nb_line_deleted_tDBOutput_2 + deletedCount_tDBOutput_2;
				nb_line_update_tDBOutput_2 = nb_line_update_tDBOutput_2 + updatedCount_tDBOutput_2;
				nb_line_inserted_tDBOutput_2 = nb_line_inserted_tDBOutput_2 + insertedCount_tDBOutput_2;
				nb_line_rejected_tDBOutput_2 = nb_line_rejected_tDBOutput_2 + rejectedCount_tDBOutput_2;

				globalMap.put("tDBOutput_2_NB_LINE", nb_line_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_UPDATED", nb_line_update_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_DELETED", nb_line_deleted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_2);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "Out_Location");
				}

				ok_Hash.put("tDBOutput_2", true);
				end_Hash.put("tDBOutput_2", System.currentTimeMillis());

				/**
				 * [tDBOutput_2 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tFileInputDelimited_2:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk2", 0, "ok");
			}

			tFileInputDelimited_3Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_2 finally ] start
				 */

				currentComponent = "tFileInputDelimited_2";

				/**
				 * [tFileInputDelimited_2 finally ] stop
				 */

				/**
				 * [tUniqRow_2 finally ] start
				 */

				currentComponent = "tUniqRow_2";

				/**
				 * [tUniqRow_2 finally ] stop
				 */

				/**
				 * [tMap_2 finally ] start
				 */

				currentComponent = "tMap_2";

				/**
				 * [tMap_2 finally ] stop
				 */

				/**
				 * [tDBOutput_2 finally ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_2") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_2 = null;
						if ((pstmtToClose_tDBOutput_2 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_2")) != null) {
							pstmtToClose_tDBOutput_2.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_2") == null) {
						java.sql.Connection ctn_tDBOutput_2 = null;
						if ((ctn_tDBOutput_2 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_2")) != null) {
							try {
								ctn_tDBOutput_2.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_2) {
								String errorMessage_tDBOutput_2 = "failed to close the connection in tDBOutput_2 :"
										+ sqlEx_tDBOutput_2.getMessage();
								System.err.println(errorMessage_tDBOutput_2);
							}
						}
					}
				}

				/**
				 * [tDBOutput_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", 1);
	}

	public static class Out_Location2Struct implements routines.system.IPersistableRow<Out_Location2Struct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int customer_key;

		public int getCustomer_key() {
			return this.customer_key;
		}

		public String customer_id_source;

		public String getCustomer_id_source() {
			return this.customer_id_source;
		}

		public String customer_name;

		public String getCustomer_name() {
			return this.customer_name;
		}

		public String segment;

		public String getSegment() {
			return this.segment;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.customer_key;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Out_Location2Struct other = (Out_Location2Struct) obj;

			if (this.customer_key != other.customer_key)
				return false;

			return true;
		}

		public void copyDataTo(Out_Location2Struct other) {

			other.customer_key = this.customer_key;
			other.customer_id_source = this.customer_id_source;
			other.customer_name = this.customer_name;
			other.segment = this.segment;

		}

		public void copyKeysDataTo(Out_Location2Struct other) {

			other.customer_key = this.customer_key;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.customer_key = dis.readInt();

					this.customer_id_source = readString(dis);

					this.customer_name = readString(dis);

					this.segment = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.customer_key = dis.readInt();

					this.customer_id_source = readString(dis);

					this.customer_name = readString(dis);

					this.segment = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.customer_key);

				// String

				writeString(this.customer_id_source, dos);

				// String

				writeString(this.customer_name, dos);

				// String

				writeString(this.segment, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.customer_key);

				// String

				writeString(this.customer_id_source, dos);

				// String

				writeString(this.customer_name, dos);

				// String

				writeString(this.segment, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("customer_key=" + String.valueOf(customer_key));
			sb.append(",customer_id_source=" + customer_id_source);
			sb.append(",customer_name=" + customer_name);
			sb.append(",segment=" + segment);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(Out_Location2Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.customer_key, other.customer_key);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row6Struct implements routines.system.IPersistableRow<row6Struct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];

		public Integer Row_ID;

		public Integer getRow_ID() {
			return this.Row_ID;
		}

		public String Order_ID;

		public String getOrder_ID() {
			return this.Order_ID;
		}

		public java.util.Date Order_Date;

		public java.util.Date getOrder_Date() {
			return this.Order_Date;
		}

		public java.util.Date Ship_Date;

		public java.util.Date getShip_Date() {
			return this.Ship_Date;
		}

		public String Ship_Mode;

		public String getShip_Mode() {
			return this.Ship_Mode;
		}

		public String Customer_ID;

		public String getCustomer_ID() {
			return this.Customer_ID;
		}

		public String Customer_Name;

		public String getCustomer_Name() {
			return this.Customer_Name;
		}

		public String Segment;

		public String getSegment() {
			return this.Segment;
		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public String State;

		public String getState() {
			return this.State;
		}

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Postal_Code;

		public String getPostal_Code() {
			return this.Postal_Code;
		}

		public String Market;

		public String getMarket() {
			return this.Market;
		}

		public String Region;

		public String getRegion() {
			return this.Region;
		}

		public String Product_ID;

		public String getProduct_ID() {
			return this.Product_ID;
		}

		public String Category;

		public String getCategory() {
			return this.Category;
		}

		public String Sub_Category;

		public String getSub_Category() {
			return this.Sub_Category;
		}

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Float Sales;

		public Float getSales() {
			return this.Sales;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public Float Discount;

		public Float getDiscount() {
			return this.Discount;
		}

		public Double Profit;

		public Double getProfit() {
			return this.Profit;
		}

		public Float Shipping_Cost;

		public Float getShipping_Cost() {
			return this.Shipping_Cost;
		}

		public String Order_Priority;

		public String getOrder_Priority() {
			return this.Order_Priority;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Row_ID=" + String.valueOf(Row_ID));
			sb.append(",Order_ID=" + Order_ID);
			sb.append(",Order_Date=" + String.valueOf(Order_Date));
			sb.append(",Ship_Date=" + String.valueOf(Ship_Date));
			sb.append(",Ship_Mode=" + Ship_Mode);
			sb.append(",Customer_ID=" + Customer_ID);
			sb.append(",Customer_Name=" + Customer_Name);
			sb.append(",Segment=" + Segment);
			sb.append(",City=" + City);
			sb.append(",State=" + State);
			sb.append(",Country=" + Country);
			sb.append(",Postal_Code=" + Postal_Code);
			sb.append(",Market=" + Market);
			sb.append(",Region=" + Region);
			sb.append(",Product_ID=" + Product_ID);
			sb.append(",Category=" + Category);
			sb.append(",Sub_Category=" + Sub_Category);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Sales=" + String.valueOf(Sales));
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",Discount=" + String.valueOf(Discount));
			sb.append(",Profit=" + String.valueOf(Profit));
			sb.append(",Shipping_Cost=" + String.valueOf(Shipping_Cost));
			sb.append(",Order_Priority=" + Order_Priority);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row6Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row5Struct implements routines.system.IPersistableRow<row5Struct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer Row_ID;

		public Integer getRow_ID() {
			return this.Row_ID;
		}

		public String Order_ID;

		public String getOrder_ID() {
			return this.Order_ID;
		}

		public java.util.Date Order_Date;

		public java.util.Date getOrder_Date() {
			return this.Order_Date;
		}

		public java.util.Date Ship_Date;

		public java.util.Date getShip_Date() {
			return this.Ship_Date;
		}

		public String Ship_Mode;

		public String getShip_Mode() {
			return this.Ship_Mode;
		}

		public String Customer_ID;

		public String getCustomer_ID() {
			return this.Customer_ID;
		}

		public String Customer_Name;

		public String getCustomer_Name() {
			return this.Customer_Name;
		}

		public String Segment;

		public String getSegment() {
			return this.Segment;
		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public String State;

		public String getState() {
			return this.State;
		}

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Postal_Code;

		public String getPostal_Code() {
			return this.Postal_Code;
		}

		public String Market;

		public String getMarket() {
			return this.Market;
		}

		public String Region;

		public String getRegion() {
			return this.Region;
		}

		public String Product_ID;

		public String getProduct_ID() {
			return this.Product_ID;
		}

		public String Category;

		public String getCategory() {
			return this.Category;
		}

		public String Sub_Category;

		public String getSub_Category() {
			return this.Sub_Category;
		}

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Float Sales;

		public Float getSales() {
			return this.Sales;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public Float Discount;

		public Float getDiscount() {
			return this.Discount;
		}

		public Double Profit;

		public Double getProfit() {
			return this.Profit;
		}

		public Float Shipping_Cost;

		public Float getShipping_Cost() {
			return this.Shipping_Cost;
		}

		public String Order_Priority;

		public String getOrder_Priority() {
			return this.Order_Priority;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Row_ID == null) ? 0 : this.Row_ID.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row5Struct other = (row5Struct) obj;

			if (this.Row_ID == null) {
				if (other.Row_ID != null)
					return false;

			} else if (!this.Row_ID.equals(other.Row_ID))

				return false;

			return true;
		}

		public void copyDataTo(row5Struct other) {

			other.Row_ID = this.Row_ID;
			other.Order_ID = this.Order_ID;
			other.Order_Date = this.Order_Date;
			other.Ship_Date = this.Ship_Date;
			other.Ship_Mode = this.Ship_Mode;
			other.Customer_ID = this.Customer_ID;
			other.Customer_Name = this.Customer_Name;
			other.Segment = this.Segment;
			other.City = this.City;
			other.State = this.State;
			other.Country = this.Country;
			other.Postal_Code = this.Postal_Code;
			other.Market = this.Market;
			other.Region = this.Region;
			other.Product_ID = this.Product_ID;
			other.Category = this.Category;
			other.Sub_Category = this.Sub_Category;
			other.Product_Name = this.Product_Name;
			other.Sales = this.Sales;
			other.Quantity = this.Quantity;
			other.Discount = this.Discount;
			other.Profit = this.Profit;
			other.Shipping_Cost = this.Shipping_Cost;
			other.Order_Priority = this.Order_Priority;

		}

		public void copyKeysDataTo(row5Struct other) {

			other.Row_ID = this.Row_ID;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Row_ID=" + String.valueOf(Row_ID));
			sb.append(",Order_ID=" + Order_ID);
			sb.append(",Order_Date=" + String.valueOf(Order_Date));
			sb.append(",Ship_Date=" + String.valueOf(Ship_Date));
			sb.append(",Ship_Mode=" + Ship_Mode);
			sb.append(",Customer_ID=" + Customer_ID);
			sb.append(",Customer_Name=" + Customer_Name);
			sb.append(",Segment=" + Segment);
			sb.append(",City=" + City);
			sb.append(",State=" + State);
			sb.append(",Country=" + Country);
			sb.append(",Postal_Code=" + Postal_Code);
			sb.append(",Market=" + Market);
			sb.append(",Region=" + Region);
			sb.append(",Product_ID=" + Product_ID);
			sb.append(",Category=" + Category);
			sb.append(",Sub_Category=" + Sub_Category);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Sales=" + String.valueOf(Sales));
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",Discount=" + String.valueOf(Discount));
			sb.append(",Profit=" + String.valueOf(Profit));
			sb.append(",Shipping_Cost=" + String.valueOf(Shipping_Cost));
			sb.append(",Order_Priority=" + Order_Priority);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row5Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Row_ID, other.Row_ID);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row5Struct row5 = new row5Struct();
				row6Struct row6 = new row6Struct();
				Out_Location2Struct Out_Location2 = new Out_Location2Struct();

				/**
				 * [tDBOutput_3 begin ] start
				 */

				ok_Hash.put("tDBOutput_3", false);
				start_Hash.put("tDBOutput_3", System.currentTimeMillis());

				currentComponent = "tDBOutput_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "Out_Location2");
				}

				int tos_count_tDBOutput_3 = 0;

				String dbschema_tDBOutput_3 = null;
				dbschema_tDBOutput_3 = "public";

				String tableName_tDBOutput_3 = null;
				if (dbschema_tDBOutput_3 == null || dbschema_tDBOutput_3.trim().length() == 0) {
					tableName_tDBOutput_3 = ("dim_customer");
				} else {
					tableName_tDBOutput_3 = dbschema_tDBOutput_3 + "\".\"" + ("dim_customer");
				}

				int nb_line_tDBOutput_3 = 0;
				int nb_line_update_tDBOutput_3 = 0;
				int nb_line_inserted_tDBOutput_3 = 0;
				int nb_line_deleted_tDBOutput_3 = 0;
				int nb_line_rejected_tDBOutput_3 = 0;

				int deletedCount_tDBOutput_3 = 0;
				int updatedCount_tDBOutput_3 = 0;
				int insertedCount_tDBOutput_3 = 0;
				int rowsToCommitCount_tDBOutput_3 = 0;
				int rejectedCount_tDBOutput_3 = 0;

				boolean whetherReject_tDBOutput_3 = false;

				java.sql.Connection conn_tDBOutput_3 = null;
				String dbUser_tDBOutput_3 = null;

				java.lang.Class.forName("org.postgresql.Driver");

				String url_tDBOutput_3 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "GlobalRetailDW";
				dbUser_tDBOutput_3 = "postgres";

				final String decryptedPassword_tDBOutput_3 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:R+NAuHkRu45FlJSRFUgQ2Ux4lCPrTY9aHuojctguQxUvbl9TwA==");

				String dbPwd_tDBOutput_3 = decryptedPassword_tDBOutput_3;

				conn_tDBOutput_3 = java.sql.DriverManager.getConnection(url_tDBOutput_3, dbUser_tDBOutput_3,
						dbPwd_tDBOutput_3);

				resourceMap.put("conn_tDBOutput_3", conn_tDBOutput_3);
				conn_tDBOutput_3.setAutoCommit(false);
				int commitEvery_tDBOutput_3 = 10000;
				int commitCounter_tDBOutput_3 = 0;

				int batchSize_tDBOutput_3 = 10000;
				int batchSizeCounter_tDBOutput_3 = 0;

				int count_tDBOutput_3 = 0;
				String insert_tDBOutput_3 = "INSERT INTO \"" + tableName_tDBOutput_3
						+ "\" (\"customer_id_source\",\"customer_name\",\"segment\") VALUES (?,?,?)";

				java.sql.PreparedStatement pstmt_tDBOutput_3 = conn_tDBOutput_3.prepareStatement(insert_tDBOutput_3);
				resourceMap.put("pstmt_tDBOutput_3", pstmt_tDBOutput_3);

				/**
				 * [tDBOutput_3 begin ] stop
				 */

				/**
				 * [tMap_3 begin ] start
				 */

				ok_Hash.put("tMap_3", false);
				start_Hash.put("tMap_3", System.currentTimeMillis());

				currentComponent = "tMap_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row6");
				}

				int tos_count_tMap_3 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_3__Struct {
				}
				Var__tMap_3__Struct Var__tMap_3 = new Var__tMap_3__Struct();
// ###############################

// ###############################
// # Outputs initialization
				Out_Location2Struct Out_Location2_tmp = new Out_Location2Struct();
// ###############################

				/**
				 * [tMap_3 begin ] stop
				 */

				/**
				 * [tUniqRow_3 begin ] start
				 */

				ok_Hash.put("tUniqRow_3", false);
				start_Hash.put("tUniqRow_3", System.currentTimeMillis());

				currentComponent = "tUniqRow_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row5");
				}

				int tos_count_tUniqRow_3 = 0;

				class KeyStruct_tUniqRow_3 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					String Customer_ID;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.Customer_ID == null) ? 0 : this.Customer_ID.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_3 other = (KeyStruct_tUniqRow_3) obj;

						if (this.Customer_ID == null) {
							if (other.Customer_ID != null)
								return false;

						} else if (!this.Customer_ID.equals(other.Customer_ID))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_3 = 0;
				int nb_duplicates_tUniqRow_3 = 0;
				KeyStruct_tUniqRow_3 finder_tUniqRow_3 = new KeyStruct_tUniqRow_3();
				java.util.Set<KeyStruct_tUniqRow_3> keystUniqRow_3 = new java.util.HashSet<KeyStruct_tUniqRow_3>();

				/**
				 * [tUniqRow_3 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_3 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_3", false);
				start_Hash.put("tFileInputDelimited_3", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_3";

				int tos_count_tFileInputDelimited_3 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_3 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_3 = 0;
				int footer_tFileInputDelimited_3 = 0;
				int totalLinetFileInputDelimited_3 = 0;
				int limittFileInputDelimited_3 = -1;
				int lastLinetFileInputDelimited_3 = -1;

				char fieldSeparator_tFileInputDelimited_3[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ";").length() > 0) {
					fieldSeparator_tFileInputDelimited_3 = ((String) ";").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_3[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_3 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_3 = /** Start field tFileInputDelimited_3:FILENAME */
						"C:/Users/DELL/Desktop/GlobalRetail_BI_360/data/generated_sources/Source_ERP_Ventes.csv"/**
																												 * End
																												 * field
																												 * tFileInputDelimited_3:FILENAME
																												 */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_3 = null;

				try {

					String[] rowtFileInputDelimited_3 = null;
					int currentLinetFileInputDelimited_3 = 0;
					int outputLinetFileInputDelimited_3 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_3 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_3 = 0;
							if (footer_value_tFileInputDelimited_3 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_3 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_3,
									fieldSeparator_tFileInputDelimited_3[0], "UTF-8");
						} else {
							csvReadertFileInputDelimited_3 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_3),
									fieldSeparator_tFileInputDelimited_3[0], "UTF-8");
						}

						csvReadertFileInputDelimited_3.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_3[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_3[0] != '\r'))
							csvReadertFileInputDelimited_3.setLineEnd("" + rowSeparator_tFileInputDelimited_3[0]);

						csvReadertFileInputDelimited_3.setQuoteChar('"');

						csvReadertFileInputDelimited_3.setEscapeChar(csvReadertFileInputDelimited_3.getQuoteChar());

						if (footer_tFileInputDelimited_3 > 0) {
							for (totalLinetFileInputDelimited_3 = 0; totalLinetFileInputDelimited_3 < 1; totalLinetFileInputDelimited_3++) {
								csvReadertFileInputDelimited_3.readNext();
							}
							csvReadertFileInputDelimited_3.setSkipEmptyRecords(false);
							while (csvReadertFileInputDelimited_3.readNext()) {

								totalLinetFileInputDelimited_3++;

							}
							int lastLineTemptFileInputDelimited_3 = totalLinetFileInputDelimited_3
									- footer_tFileInputDelimited_3 < 0 ? 0
											: totalLinetFileInputDelimited_3 - footer_tFileInputDelimited_3;
							if (lastLinetFileInputDelimited_3 > 0) {
								lastLinetFileInputDelimited_3 = lastLinetFileInputDelimited_3 < lastLineTemptFileInputDelimited_3
										? lastLinetFileInputDelimited_3
										: lastLineTemptFileInputDelimited_3;
							} else {
								lastLinetFileInputDelimited_3 = lastLineTemptFileInputDelimited_3;
							}

							csvReadertFileInputDelimited_3.close();
							if (filename_tFileInputDelimited_3 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_3 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_3,
										fieldSeparator_tFileInputDelimited_3[0], "UTF-8");
							} else {
								csvReadertFileInputDelimited_3 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_3),
										fieldSeparator_tFileInputDelimited_3[0], "UTF-8");
							}
							csvReadertFileInputDelimited_3.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_3[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_3[0] != '\r'))
								csvReadertFileInputDelimited_3.setLineEnd("" + rowSeparator_tFileInputDelimited_3[0]);

							csvReadertFileInputDelimited_3.setQuoteChar('"');

							csvReadertFileInputDelimited_3.setEscapeChar(csvReadertFileInputDelimited_3.getQuoteChar());

						}

						if (limittFileInputDelimited_3 != 0) {
							for (currentLinetFileInputDelimited_3 = 0; currentLinetFileInputDelimited_3 < 1; currentLinetFileInputDelimited_3++) {
								csvReadertFileInputDelimited_3.readNext();
							}
						}
						csvReadertFileInputDelimited_3.setSkipEmptyRecords(false);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					while (limittFileInputDelimited_3 != 0 && csvReadertFileInputDelimited_3 != null
							&& csvReadertFileInputDelimited_3.readNext()) {
						rowstate_tFileInputDelimited_3.reset();

						rowtFileInputDelimited_3 = csvReadertFileInputDelimited_3.getValues();

						currentLinetFileInputDelimited_3++;

						if (lastLinetFileInputDelimited_3 > -1
								&& currentLinetFileInputDelimited_3 > lastLinetFileInputDelimited_3) {
							break;
						}
						outputLinetFileInputDelimited_3++;
						if (limittFileInputDelimited_3 > 0
								&& outputLinetFileInputDelimited_3 > limittFileInputDelimited_3) {
							break;
						}

						row5 = null;

						boolean whetherReject_tFileInputDelimited_3 = false;
						row5 = new row5Struct();
						try {

							char fieldSeparator_tFileInputDelimited_3_ListType[] = null;
							// support passing value (property: Field Separator) by 'context.fs' or
							// 'globalMap.get("fs")'.
							if (((String) ";").length() > 0) {
								fieldSeparator_tFileInputDelimited_3_ListType = ((String) ";").toCharArray();
							} else {
								throw new IllegalArgumentException("Field Separator must be assigned a char.");
							}
							if (rowtFileInputDelimited_3.length == 1 && ("\015").equals(rowtFileInputDelimited_3[0])) {// empty
																														// line
																														// when
																														// row
																														// separator
																														// is
																														// '\n'

								row5.Row_ID = null;

								row5.Order_ID = null;

								row5.Order_Date = null;

								row5.Ship_Date = null;

								row5.Ship_Mode = null;

								row5.Customer_ID = null;

								row5.Customer_Name = null;

								row5.Segment = null;

								row5.City = null;

								row5.State = null;

								row5.Country = null;

								row5.Postal_Code = null;

								row5.Market = null;

								row5.Region = null;

								row5.Product_ID = null;

								row5.Category = null;

								row5.Sub_Category = null;

								row5.Product_Name = null;

								row5.Sales = null;

								row5.Quantity = null;

								row5.Discount = null;

								row5.Profit = null;

								row5.Shipping_Cost = null;

								row5.Order_Priority = null;

							} else {

								int columnIndexWithD_tFileInputDelimited_3 = 0; // Column Index

								columnIndexWithD_tFileInputDelimited_3 = 0;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									if (rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3].length() > 0) {
										try {

											row5.Row_ID = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3]);

										} catch (java.lang.Exception ex_tFileInputDelimited_3) {
											globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE",
													ex_tFileInputDelimited_3.getMessage());
											rowstate_tFileInputDelimited_3.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Row_ID", "row5",
															rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
															ex_tFileInputDelimited_3),
													ex_tFileInputDelimited_3));
										}
									} else {

										row5.Row_ID = null;

									}

								} else {

									row5.Row_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 1;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Order_ID = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Order_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 2;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									if (rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3].length() > 0) {
										try {

											row5.Order_Date = ParserUtils.parseTo_Date(
													rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
													"dd-MM-yyyy");

										} catch (java.lang.Exception ex_tFileInputDelimited_3) {
											globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE",
													ex_tFileInputDelimited_3.getMessage());
											rowstate_tFileInputDelimited_3.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Order_Date", "row5",
															rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
															ex_tFileInputDelimited_3),
													ex_tFileInputDelimited_3));
										}
									} else {

										row5.Order_Date = null;

									}

								} else {

									row5.Order_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 3;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									if (rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3].length() > 0) {
										try {

											row5.Ship_Date = ParserUtils.parseTo_Date(
													rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
													"dd-MM-yyyy");

										} catch (java.lang.Exception ex_tFileInputDelimited_3) {
											globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE",
													ex_tFileInputDelimited_3.getMessage());
											rowstate_tFileInputDelimited_3.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Ship_Date", "row5",
															rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
															ex_tFileInputDelimited_3),
													ex_tFileInputDelimited_3));
										}
									} else {

										row5.Ship_Date = null;

									}

								} else {

									row5.Ship_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 4;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Ship_Mode = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Ship_Mode = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 5;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Customer_ID = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Customer_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 6;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Customer_Name = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Customer_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 7;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Segment = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Segment = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 8;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.City = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.City = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 9;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.State = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.State = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 10;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Country = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Country = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 11;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Postal_Code = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Postal_Code = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 12;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Market = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Market = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 13;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Region = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Region = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 14;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Product_ID = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Product_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 15;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Category = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Category = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 16;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Sub_Category = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Sub_Category = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 17;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Product_Name = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Product_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 18;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									if (rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3].length() > 0) {
										try {

											row5.Sales = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3]);

										} catch (java.lang.Exception ex_tFileInputDelimited_3) {
											globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE",
													ex_tFileInputDelimited_3.getMessage());
											rowstate_tFileInputDelimited_3.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Sales", "row5",
															rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
															ex_tFileInputDelimited_3),
													ex_tFileInputDelimited_3));
										}
									} else {

										row5.Sales = null;

									}

								} else {

									row5.Sales = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 19;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									if (rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3].length() > 0) {
										try {

											row5.Quantity = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3]);

										} catch (java.lang.Exception ex_tFileInputDelimited_3) {
											globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE",
													ex_tFileInputDelimited_3.getMessage());
											rowstate_tFileInputDelimited_3.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Quantity", "row5",
															rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
															ex_tFileInputDelimited_3),
													ex_tFileInputDelimited_3));
										}
									} else {

										row5.Quantity = null;

									}

								} else {

									row5.Quantity = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 20;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									if (rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3].length() > 0) {
										try {

											row5.Discount = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3]);

										} catch (java.lang.Exception ex_tFileInputDelimited_3) {
											globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE",
													ex_tFileInputDelimited_3.getMessage());
											rowstate_tFileInputDelimited_3.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Discount", "row5",
															rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
															ex_tFileInputDelimited_3),
													ex_tFileInputDelimited_3));
										}
									} else {

										row5.Discount = null;

									}

								} else {

									row5.Discount = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 21;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									if (rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3].length() > 0) {
										try {

											row5.Profit = ParserUtils.parseTo_Double(
													rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3]);

										} catch (java.lang.Exception ex_tFileInputDelimited_3) {
											globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE",
													ex_tFileInputDelimited_3.getMessage());
											rowstate_tFileInputDelimited_3.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Profit", "row5",
															rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
															ex_tFileInputDelimited_3),
													ex_tFileInputDelimited_3));
										}
									} else {

										row5.Profit = null;

									}

								} else {

									row5.Profit = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 22;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									if (rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3].length() > 0) {
										try {

											row5.Shipping_Cost = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3]);

										} catch (java.lang.Exception ex_tFileInputDelimited_3) {
											globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE",
													ex_tFileInputDelimited_3.getMessage());
											rowstate_tFileInputDelimited_3.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Shipping_Cost", "row5",
															rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
															ex_tFileInputDelimited_3),
													ex_tFileInputDelimited_3));
										}
									} else {

										row5.Shipping_Cost = null;

									}

								} else {

									row5.Shipping_Cost = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 23;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row5.Order_Priority = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row5.Order_Priority = null;

								}

							}

							if (rowstate_tFileInputDelimited_3.getException() != null) {
								throw rowstate_tFileInputDelimited_3.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_3 = true;

							System.err.println(e.getMessage());
							row5 = null;

							globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE", e.getMessage());

						}

						/**
						 * [tFileInputDelimited_3 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_3 main ] start
						 */

						currentComponent = "tFileInputDelimited_3";

						tos_count_tFileInputDelimited_3++;

						/**
						 * [tFileInputDelimited_3 main ] stop
						 */

						/**
						 * [tFileInputDelimited_3 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_3";

						/**
						 * [tFileInputDelimited_3 process_data_begin ] stop
						 */
// Start of branch "row5"
						if (row5 != null) {

							/**
							 * [tUniqRow_3 main ] start
							 */

							currentComponent = "tUniqRow_3";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row5"

								);
							}

							row6 = null;
							if (row5.Customer_ID == null) {
								finder_tUniqRow_3.Customer_ID = null;
							} else {
								finder_tUniqRow_3.Customer_ID = row5.Customer_ID.toLowerCase();
							}
							finder_tUniqRow_3.hashCodeDirty = true;
							if (!keystUniqRow_3.contains(finder_tUniqRow_3)) {
								KeyStruct_tUniqRow_3 new_tUniqRow_3 = new KeyStruct_tUniqRow_3();

								if (row5.Customer_ID == null) {
									new_tUniqRow_3.Customer_ID = null;
								} else {
									new_tUniqRow_3.Customer_ID = row5.Customer_ID.toLowerCase();
								}

								keystUniqRow_3.add(new_tUniqRow_3);
								if (row6 == null) {

									row6 = new row6Struct();
								}
								row6.Row_ID = row5.Row_ID;
								row6.Order_ID = row5.Order_ID;
								row6.Order_Date = row5.Order_Date;
								row6.Ship_Date = row5.Ship_Date;
								row6.Ship_Mode = row5.Ship_Mode;
								row6.Customer_ID = row5.Customer_ID;
								row6.Customer_Name = row5.Customer_Name;
								row6.Segment = row5.Segment;
								row6.City = row5.City;
								row6.State = row5.State;
								row6.Country = row5.Country;
								row6.Postal_Code = row5.Postal_Code;
								row6.Market = row5.Market;
								row6.Region = row5.Region;
								row6.Product_ID = row5.Product_ID;
								row6.Category = row5.Category;
								row6.Sub_Category = row5.Sub_Category;
								row6.Product_Name = row5.Product_Name;
								row6.Sales = row5.Sales;
								row6.Quantity = row5.Quantity;
								row6.Discount = row5.Discount;
								row6.Profit = row5.Profit;
								row6.Shipping_Cost = row5.Shipping_Cost;
								row6.Order_Priority = row5.Order_Priority;
								nb_uniques_tUniqRow_3++;
							} else {
								nb_duplicates_tUniqRow_3++;
							}

							tos_count_tUniqRow_3++;

							/**
							 * [tUniqRow_3 main ] stop
							 */

							/**
							 * [tUniqRow_3 process_data_begin ] start
							 */

							currentComponent = "tUniqRow_3";

							/**
							 * [tUniqRow_3 process_data_begin ] stop
							 */
// Start of branch "row6"
							if (row6 != null) {

								/**
								 * [tMap_3 main ] start
								 */

								currentComponent = "tMap_3";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row6"

									);
								}

								boolean hasCasePrimitiveKeyWithNull_tMap_3 = false;

								// ###############################
								// # Input tables (lookups)
								boolean rejectedInnerJoin_tMap_3 = false;
								boolean mainRowRejected_tMap_3 = false;

								// ###############################
								{ // start of Var scope

									// ###############################
									// # Vars tables

									Var__tMap_3__Struct Var = Var__tMap_3;// ###############################
									// ###############################
									// # Output tables

									Out_Location2 = null;

// # Output table : 'Out_Location2'
									Out_Location2_tmp.customer_key = 0;
									Out_Location2_tmp.customer_id_source = row6.Customer_ID;
									Out_Location2_tmp.customer_name = row6.Customer_Name;
									Out_Location2_tmp.segment = row6.Segment;
									Out_Location2 = Out_Location2_tmp;
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_3 = false;

								tos_count_tMap_3++;

								/**
								 * [tMap_3 main ] stop
								 */

								/**
								 * [tMap_3 process_data_begin ] start
								 */

								currentComponent = "tMap_3";

								/**
								 * [tMap_3 process_data_begin ] stop
								 */
// Start of branch "Out_Location2"
								if (Out_Location2 != null) {

									/**
									 * [tDBOutput_3 main ] start
									 */

									currentComponent = "tDBOutput_3";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "Out_Location2"

										);
									}

									whetherReject_tDBOutput_3 = false;
									if (Out_Location2.customer_id_source == null) {
										pstmt_tDBOutput_3.setNull(1, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(1, Out_Location2.customer_id_source);
									}

									if (Out_Location2.customer_name == null) {
										pstmt_tDBOutput_3.setNull(2, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(2, Out_Location2.customer_name);
									}

									if (Out_Location2.segment == null) {
										pstmt_tDBOutput_3.setNull(3, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_3.setString(3, Out_Location2.segment);
									}

									pstmt_tDBOutput_3.addBatch();
									nb_line_tDBOutput_3++;

									batchSizeCounter_tDBOutput_3++;

									if ((batchSize_tDBOutput_3 > 0)
											&& (batchSize_tDBOutput_3 <= batchSizeCounter_tDBOutput_3)) {
										try {
											int countSum_tDBOutput_3 = 0;

											for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
												countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0
														: countEach_tDBOutput_3);
											}
											rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

											insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

											batchSizeCounter_tDBOutput_3 = 0;
										} catch (java.sql.BatchUpdateException e_tDBOutput_3) {
											globalMap.put("tDBOutput_3_ERROR_MESSAGE", e_tDBOutput_3.getMessage());
											java.sql.SQLException ne_tDBOutput_3 = e_tDBOutput_3.getNextException(),
													sqle_tDBOutput_3 = null;
											String errormessage_tDBOutput_3;
											if (ne_tDBOutput_3 != null) {
												// build new exception to provide the original cause
												sqle_tDBOutput_3 = new java.sql.SQLException(
														e_tDBOutput_3.getMessage() + "\ncaused by: "
																+ ne_tDBOutput_3.getMessage(),
														ne_tDBOutput_3.getSQLState(), ne_tDBOutput_3.getErrorCode(),
														ne_tDBOutput_3);
												errormessage_tDBOutput_3 = sqle_tDBOutput_3.getMessage();
											} else {
												errormessage_tDBOutput_3 = e_tDBOutput_3.getMessage();
											}

											int countSum_tDBOutput_3 = 0;
											for (int countEach_tDBOutput_3 : e_tDBOutput_3.getUpdateCounts()) {
												countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0
														: countEach_tDBOutput_3);
											}
											rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

											insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

											System.err.println(errormessage_tDBOutput_3);

										}
									}

									commitCounter_tDBOutput_3++;
									if (commitEvery_tDBOutput_3 <= commitCounter_tDBOutput_3) {
										if ((batchSize_tDBOutput_3 > 0) && (batchSizeCounter_tDBOutput_3 > 0)) {
											try {
												int countSum_tDBOutput_3 = 0;

												for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
													countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0
															: countEach_tDBOutput_3);
												}
												rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

												insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

												batchSizeCounter_tDBOutput_3 = 0;
											} catch (java.sql.BatchUpdateException e_tDBOutput_3) {
												globalMap.put("tDBOutput_3_ERROR_MESSAGE", e_tDBOutput_3.getMessage());
												java.sql.SQLException ne_tDBOutput_3 = e_tDBOutput_3.getNextException(),
														sqle_tDBOutput_3 = null;
												String errormessage_tDBOutput_3;
												if (ne_tDBOutput_3 != null) {
													// build new exception to provide the original cause
													sqle_tDBOutput_3 = new java.sql.SQLException(
															e_tDBOutput_3.getMessage() + "\ncaused by: "
																	+ ne_tDBOutput_3.getMessage(),
															ne_tDBOutput_3.getSQLState(), ne_tDBOutput_3.getErrorCode(),
															ne_tDBOutput_3);
													errormessage_tDBOutput_3 = sqle_tDBOutput_3.getMessage();
												} else {
													errormessage_tDBOutput_3 = e_tDBOutput_3.getMessage();
												}

												int countSum_tDBOutput_3 = 0;
												for (int countEach_tDBOutput_3 : e_tDBOutput_3.getUpdateCounts()) {
													countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0
															: countEach_tDBOutput_3);
												}
												rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

												insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

												System.err.println(errormessage_tDBOutput_3);

											}
										}
										if (rowsToCommitCount_tDBOutput_3 != 0) {

										}
										conn_tDBOutput_3.commit();
										if (rowsToCommitCount_tDBOutput_3 != 0) {

											rowsToCommitCount_tDBOutput_3 = 0;
										}
										commitCounter_tDBOutput_3 = 0;
									}

									tos_count_tDBOutput_3++;

									/**
									 * [tDBOutput_3 main ] stop
									 */

									/**
									 * [tDBOutput_3 process_data_begin ] start
									 */

									currentComponent = "tDBOutput_3";

									/**
									 * [tDBOutput_3 process_data_begin ] stop
									 */

									/**
									 * [tDBOutput_3 process_data_end ] start
									 */

									currentComponent = "tDBOutput_3";

									/**
									 * [tDBOutput_3 process_data_end ] stop
									 */

								} // End of branch "Out_Location2"

								/**
								 * [tMap_3 process_data_end ] start
								 */

								currentComponent = "tMap_3";

								/**
								 * [tMap_3 process_data_end ] stop
								 */

							} // End of branch "row6"

							/**
							 * [tUniqRow_3 process_data_end ] start
							 */

							currentComponent = "tUniqRow_3";

							/**
							 * [tUniqRow_3 process_data_end ] stop
							 */

						} // End of branch "row5"

						/**
						 * [tFileInputDelimited_3 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_3";

						/**
						 * [tFileInputDelimited_3 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_3 end ] start
						 */

						currentComponent = "tFileInputDelimited_3";

						nb_line_tFileInputDelimited_3++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_3 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_3 != null) {
							csvReadertFileInputDelimited_3.close();
						}
					}
					if (csvReadertFileInputDelimited_3 != null) {
						globalMap.put("tFileInputDelimited_3_NB_LINE", nb_line_tFileInputDelimited_3);
					}

				}

				ok_Hash.put("tFileInputDelimited_3", true);
				end_Hash.put("tFileInputDelimited_3", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_3 end ] stop
				 */

				/**
				 * [tUniqRow_3 end ] start
				 */

				currentComponent = "tUniqRow_3";

				globalMap.put("tUniqRow_3_NB_UNIQUES", nb_uniques_tUniqRow_3);
				globalMap.put("tUniqRow_3_NB_DUPLICATES", nb_duplicates_tUniqRow_3);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row5");
				}

				ok_Hash.put("tUniqRow_3", true);
				end_Hash.put("tUniqRow_3", System.currentTimeMillis());

				/**
				 * [tUniqRow_3 end ] stop
				 */

				/**
				 * [tMap_3 end ] start
				 */

				currentComponent = "tMap_3";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row6");
				}

				ok_Hash.put("tMap_3", true);
				end_Hash.put("tMap_3", System.currentTimeMillis());

				/**
				 * [tMap_3 end ] stop
				 */

				/**
				 * [tDBOutput_3 end ] start
				 */

				currentComponent = "tDBOutput_3";

				try {
					int countSum_tDBOutput_3 = 0;
					if (pstmt_tDBOutput_3 != null && batchSizeCounter_tDBOutput_3 > 0) {

						for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
							countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
						}
						rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

					}

					insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

				} catch (java.sql.BatchUpdateException e_tDBOutput_3) {
					globalMap.put("tDBOutput_3_ERROR_MESSAGE", e_tDBOutput_3.getMessage());
					java.sql.SQLException ne_tDBOutput_3 = e_tDBOutput_3.getNextException(), sqle_tDBOutput_3 = null;
					String errormessage_tDBOutput_3;
					if (ne_tDBOutput_3 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_3 = new java.sql.SQLException(
								e_tDBOutput_3.getMessage() + "\ncaused by: " + ne_tDBOutput_3.getMessage(),
								ne_tDBOutput_3.getSQLState(), ne_tDBOutput_3.getErrorCode(), ne_tDBOutput_3);
						errormessage_tDBOutput_3 = sqle_tDBOutput_3.getMessage();
					} else {
						errormessage_tDBOutput_3 = e_tDBOutput_3.getMessage();
					}

					int countSum_tDBOutput_3 = 0;
					for (int countEach_tDBOutput_3 : e_tDBOutput_3.getUpdateCounts()) {
						countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
					}
					rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

					insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

					System.err.println(errormessage_tDBOutput_3);

				}

				if (pstmt_tDBOutput_3 != null) {

					pstmt_tDBOutput_3.close();
					resourceMap.remove("pstmt_tDBOutput_3");
				}
				resourceMap.put("statementClosed_tDBOutput_3", true);
				if (rowsToCommitCount_tDBOutput_3 != 0) {

				}
				conn_tDBOutput_3.commit();
				if (rowsToCommitCount_tDBOutput_3 != 0) {

					rowsToCommitCount_tDBOutput_3 = 0;
				}
				commitCounter_tDBOutput_3 = 0;

				conn_tDBOutput_3.close();

				resourceMap.put("finish_tDBOutput_3", true);

				nb_line_deleted_tDBOutput_3 = nb_line_deleted_tDBOutput_3 + deletedCount_tDBOutput_3;
				nb_line_update_tDBOutput_3 = nb_line_update_tDBOutput_3 + updatedCount_tDBOutput_3;
				nb_line_inserted_tDBOutput_3 = nb_line_inserted_tDBOutput_3 + insertedCount_tDBOutput_3;
				nb_line_rejected_tDBOutput_3 = nb_line_rejected_tDBOutput_3 + rejectedCount_tDBOutput_3;

				globalMap.put("tDBOutput_3_NB_LINE", nb_line_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_UPDATED", nb_line_update_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_DELETED", nb_line_deleted_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_3);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "Out_Location2");
				}

				ok_Hash.put("tDBOutput_3", true);
				end_Hash.put("tDBOutput_3", System.currentTimeMillis());

				/**
				 * [tDBOutput_3 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tFileInputDelimited_3:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk3", 0, "ok");
			}

			tFileInputExcel_1Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_3 finally ] start
				 */

				currentComponent = "tFileInputDelimited_3";

				/**
				 * [tFileInputDelimited_3 finally ] stop
				 */

				/**
				 * [tUniqRow_3 finally ] start
				 */

				currentComponent = "tUniqRow_3";

				/**
				 * [tUniqRow_3 finally ] stop
				 */

				/**
				 * [tMap_3 finally ] start
				 */

				currentComponent = "tMap_3";

				/**
				 * [tMap_3 finally ] stop
				 */

				/**
				 * [tDBOutput_3 finally ] start
				 */

				currentComponent = "tDBOutput_3";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_3") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_3 = null;
						if ((pstmtToClose_tDBOutput_3 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_3")) != null) {
							pstmtToClose_tDBOutput_3.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_3") == null) {
						java.sql.Connection ctn_tDBOutput_3 = null;
						if ((ctn_tDBOutput_3 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_3")) != null) {
							try {
								ctn_tDBOutput_3.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_3) {
								String errorMessage_tDBOutput_3 = "failed to close the connection in tDBOutput_3 :"
										+ sqlEx_tDBOutput_3.getMessage();
								System.err.println(errorMessage_tDBOutput_3);
							}
						}
					}
				}

				/**
				 * [tDBOutput_3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_3_SUBPROCESS_STATE", 1);
	}

	public static class Out_Location3Struct implements routines.system.IPersistableRow<Out_Location3Struct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int manager_key;

		public int getManager_key() {
			return this.manager_key;
		}

		public String region;

		public String getRegion() {
			return this.region;
		}

		public String manager_name;

		public String getManager_name() {
			return this.manager_name;
		}

		public Integer yearly_target;

		public Integer getYearly_target() {
			return this.yearly_target;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.manager_key;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Out_Location3Struct other = (Out_Location3Struct) obj;

			if (this.manager_key != other.manager_key)
				return false;

			return true;
		}

		public void copyDataTo(Out_Location3Struct other) {

			other.manager_key = this.manager_key;
			other.region = this.region;
			other.manager_name = this.manager_name;
			other.yearly_target = this.yearly_target;

		}

		public void copyKeysDataTo(Out_Location3Struct other) {

			other.manager_key = this.manager_key;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.manager_key = dis.readInt();

					this.region = readString(dis);

					this.manager_name = readString(dis);

					this.yearly_target = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.manager_key = dis.readInt();

					this.region = readString(dis);

					this.manager_name = readString(dis);

					this.yearly_target = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.manager_key);

				// String

				writeString(this.region, dos);

				// String

				writeString(this.manager_name, dos);

				// Integer

				writeInteger(this.yearly_target, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.manager_key);

				// String

				writeString(this.region, dos);

				// String

				writeString(this.manager_name, dos);

				// Integer

				writeInteger(this.yearly_target, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("manager_key=" + String.valueOf(manager_key));
			sb.append(",region=" + region);
			sb.append(",manager_name=" + manager_name);
			sb.append(",yearly_target=" + String.valueOf(yearly_target));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(Out_Location3Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.manager_key, other.manager_key);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row7Struct implements routines.system.IPersistableRow<row7Struct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];

		public String Region;

		public String getRegion() {
			return this.Region;
		}

		public String Regional_Manager;

		public String getRegional_Manager() {
			return this.Regional_Manager;
		}

		public Integer Yearly_Target;

		public Integer getYearly_Target() {
			return this.Yearly_Target;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Region = readString(dis);

					this.Regional_Manager = readString(dis);

					this.Yearly_Target = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Region = readString(dis);

					this.Regional_Manager = readString(dis);

					this.Yearly_Target = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Regional_Manager, dos);

				// Integer

				writeInteger(this.Yearly_Target, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Regional_Manager, dos);

				// Integer

				writeInteger(this.Yearly_Target, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Region=" + Region);
			sb.append(",Regional_Manager=" + Regional_Manager);
			sb.append(",Yearly_Target=" + String.valueOf(Yearly_Target));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row7Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputExcel_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputExcel_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row7Struct row7 = new row7Struct();
				Out_Location3Struct Out_Location3 = new Out_Location3Struct();

				/**
				 * [tDBOutput_4 begin ] start
				 */

				ok_Hash.put("tDBOutput_4", false);
				start_Hash.put("tDBOutput_4", System.currentTimeMillis());

				currentComponent = "tDBOutput_4";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "Out_Location3");
				}

				int tos_count_tDBOutput_4 = 0;

				String dbschema_tDBOutput_4 = null;
				dbschema_tDBOutput_4 = "public";

				String tableName_tDBOutput_4 = null;
				if (dbschema_tDBOutput_4 == null || dbschema_tDBOutput_4.trim().length() == 0) {
					tableName_tDBOutput_4 = ("dim_manager");
				} else {
					tableName_tDBOutput_4 = dbschema_tDBOutput_4 + "\".\"" + ("dim_manager");
				}

				int nb_line_tDBOutput_4 = 0;
				int nb_line_update_tDBOutput_4 = 0;
				int nb_line_inserted_tDBOutput_4 = 0;
				int nb_line_deleted_tDBOutput_4 = 0;
				int nb_line_rejected_tDBOutput_4 = 0;

				int deletedCount_tDBOutput_4 = 0;
				int updatedCount_tDBOutput_4 = 0;
				int insertedCount_tDBOutput_4 = 0;
				int rowsToCommitCount_tDBOutput_4 = 0;
				int rejectedCount_tDBOutput_4 = 0;

				boolean whetherReject_tDBOutput_4 = false;

				java.sql.Connection conn_tDBOutput_4 = null;
				String dbUser_tDBOutput_4 = null;

				java.lang.Class.forName("org.postgresql.Driver");

				String url_tDBOutput_4 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "GlobalRetailDW";
				dbUser_tDBOutput_4 = "postgres";

				final String decryptedPassword_tDBOutput_4 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:sPkD+YF5JKx6MST62NU9rEodb2MzDN11tJ359cr4WzUPDVAwMg==");

				String dbPwd_tDBOutput_4 = decryptedPassword_tDBOutput_4;

				conn_tDBOutput_4 = java.sql.DriverManager.getConnection(url_tDBOutput_4, dbUser_tDBOutput_4,
						dbPwd_tDBOutput_4);

				resourceMap.put("conn_tDBOutput_4", conn_tDBOutput_4);
				conn_tDBOutput_4.setAutoCommit(false);
				int commitEvery_tDBOutput_4 = 10000;
				int commitCounter_tDBOutput_4 = 0;

				int batchSize_tDBOutput_4 = 10000;
				int batchSizeCounter_tDBOutput_4 = 0;

				int count_tDBOutput_4 = 0;
				String insert_tDBOutput_4 = "INSERT INTO \"" + tableName_tDBOutput_4
						+ "\" (\"region\",\"manager_name\",\"yearly_target\") VALUES (?,?,?)";

				java.sql.PreparedStatement pstmt_tDBOutput_4 = conn_tDBOutput_4.prepareStatement(insert_tDBOutput_4);
				resourceMap.put("pstmt_tDBOutput_4", pstmt_tDBOutput_4);

				/**
				 * [tDBOutput_4 begin ] stop
				 */

				/**
				 * [tMap_4 begin ] start
				 */

				ok_Hash.put("tMap_4", false);
				start_Hash.put("tMap_4", System.currentTimeMillis());

				currentComponent = "tMap_4";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row7");
				}

				int tos_count_tMap_4 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_4__Struct {
				}
				Var__tMap_4__Struct Var__tMap_4 = new Var__tMap_4__Struct();
// ###############################

// ###############################
// # Outputs initialization
				Out_Location3Struct Out_Location3_tmp = new Out_Location3Struct();
// ###############################

				/**
				 * [tMap_4 begin ] stop
				 */

				/**
				 * [tFileInputExcel_1 begin ] start
				 */

				ok_Hash.put("tFileInputExcel_1", false);
				start_Hash.put("tFileInputExcel_1", System.currentTimeMillis());

				currentComponent = "tFileInputExcel_1";

				int tos_count_tFileInputExcel_1 = 0;

				final String decryptedPassword_tFileInputExcel_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:lqBdlpR3PFVM7Xd1icenWMj2/3Epu6f+oDSjBQ==");
				String password_tFileInputExcel_1 = decryptedPassword_tFileInputExcel_1;
				if (password_tFileInputExcel_1.isEmpty()) {
					password_tFileInputExcel_1 = null;
				}
				class RegexUtil_tFileInputExcel_1 {

					public java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> getSheets(
							org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, String oneSheetName,
							boolean useRegex) {

						java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> list = new java.util.ArrayList<org.apache.poi.xssf.usermodel.XSSFSheet>();

						if (useRegex) {// this part process the regex issue

							java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(oneSheetName);
							for (org.apache.poi.ss.usermodel.Sheet sheet : workbook) {
								String sheetName = sheet.getSheetName();
								java.util.regex.Matcher matcher = pattern.matcher(sheetName);
								if (matcher.matches()) {
									if (sheet != null) {
										list.add((org.apache.poi.xssf.usermodel.XSSFSheet) sheet);
									}
								}
							}

						} else {
							org.apache.poi.xssf.usermodel.XSSFSheet sheet = (org.apache.poi.xssf.usermodel.XSSFSheet) workbook
									.getSheet(oneSheetName);
							if (sheet != null) {
								list.add(sheet);
							}

						}

						return list;
					}

					public java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> getSheets(
							org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, int index, boolean useRegex) {
						java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> list = new java.util.ArrayList<org.apache.poi.xssf.usermodel.XSSFSheet>();
						org.apache.poi.xssf.usermodel.XSSFSheet sheet = (org.apache.poi.xssf.usermodel.XSSFSheet) workbook
								.getSheetAt(index);
						if (sheet != null) {
							list.add(sheet);
						}
						return list;
					}

				}
				RegexUtil_tFileInputExcel_1 regexUtil_tFileInputExcel_1 = new RegexUtil_tFileInputExcel_1();

				Object source_tFileInputExcel_1 = "C:/Users/DELL/Desktop/GlobalRetail_BI_360/data/generated_sources/Source_RH_Targets.xlsx";
				org.apache.poi.xssf.usermodel.XSSFWorkbook workbook_tFileInputExcel_1 = null;

				if (source_tFileInputExcel_1 instanceof String) {
					workbook_tFileInputExcel_1 = (org.apache.poi.xssf.usermodel.XSSFWorkbook) org.apache.poi.ss.usermodel.WorkbookFactory
							.create(new java.io.File((String) source_tFileInputExcel_1), password_tFileInputExcel_1,
									true);
				} else if (source_tFileInputExcel_1 instanceof java.io.InputStream) {
					workbook_tFileInputExcel_1 = (org.apache.poi.xssf.usermodel.XSSFWorkbook) org.apache.poi.ss.usermodel.WorkbookFactory
							.create((java.io.InputStream) source_tFileInputExcel_1, password_tFileInputExcel_1);
				} else {
					workbook_tFileInputExcel_1 = null;
					throw new java.lang.Exception("The data source should be specified as Inputstream or File Path!");
				}
				try {

					java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> sheetList_tFileInputExcel_1 = new java.util.ArrayList<org.apache.poi.xssf.usermodel.XSSFSheet>();
					for (org.apache.poi.ss.usermodel.Sheet sheet_tFileInputExcel_1 : workbook_tFileInputExcel_1) {
						sheetList_tFileInputExcel_1
								.add((org.apache.poi.xssf.usermodel.XSSFSheet) sheet_tFileInputExcel_1);
					}
					if (sheetList_tFileInputExcel_1.size() <= 0) {
						throw new RuntimeException("Special sheets not exist!");
					}

					java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> sheetList_FilterNull_tFileInputExcel_1 = new java.util.ArrayList<org.apache.poi.xssf.usermodel.XSSFSheet>();
					for (org.apache.poi.xssf.usermodel.XSSFSheet sheet_FilterNull_tFileInputExcel_1 : sheetList_tFileInputExcel_1) {
						if (sheet_FilterNull_tFileInputExcel_1 != null
								&& sheetList_FilterNull_tFileInputExcel_1.iterator() != null
								&& sheet_FilterNull_tFileInputExcel_1.iterator().hasNext()) {
							sheetList_FilterNull_tFileInputExcel_1.add(sheet_FilterNull_tFileInputExcel_1);
						}
					}
					sheetList_tFileInputExcel_1 = sheetList_FilterNull_tFileInputExcel_1;
					if (sheetList_tFileInputExcel_1.size() > 0) {
						int nb_line_tFileInputExcel_1 = 0;

						int begin_line_tFileInputExcel_1 = 1;

						int footer_input_tFileInputExcel_1 = 0;

						int end_line_tFileInputExcel_1 = 0;
						for (org.apache.poi.xssf.usermodel.XSSFSheet sheet_tFileInputExcel_1 : sheetList_tFileInputExcel_1) {
							end_line_tFileInputExcel_1 += (sheet_tFileInputExcel_1.getLastRowNum() + 1);
						}
						end_line_tFileInputExcel_1 -= footer_input_tFileInputExcel_1;
						int limit_tFileInputExcel_1 = -1;
						int start_column_tFileInputExcel_1 = 1 - 1;
						int end_column_tFileInputExcel_1 = -1;

						org.apache.poi.xssf.usermodel.XSSFRow row_tFileInputExcel_1 = null;
						org.apache.poi.xssf.usermodel.XSSFSheet sheet_tFileInputExcel_1 = sheetList_tFileInputExcel_1
								.get(0);
						int rowCount_tFileInputExcel_1 = 0;
						int sheetIndex_tFileInputExcel_1 = 0;
						int currentRows_tFileInputExcel_1 = (sheetList_tFileInputExcel_1.get(0).getLastRowNum() + 1);

						// for the number format
						java.text.DecimalFormat df_tFileInputExcel_1 = new java.text.DecimalFormat(
								"#.####################################");
						char decimalChar_tFileInputExcel_1 = df_tFileInputExcel_1.getDecimalFormatSymbols()
								.getDecimalSeparator();

						for (int i_tFileInputExcel_1 = begin_line_tFileInputExcel_1; i_tFileInputExcel_1 < end_line_tFileInputExcel_1; i_tFileInputExcel_1++) {

							int emptyColumnCount_tFileInputExcel_1 = 0;

							if (limit_tFileInputExcel_1 != -1 && nb_line_tFileInputExcel_1 >= limit_tFileInputExcel_1) {
								break;
							}

							while (i_tFileInputExcel_1 >= rowCount_tFileInputExcel_1 + currentRows_tFileInputExcel_1) {
								rowCount_tFileInputExcel_1 += currentRows_tFileInputExcel_1;
								sheet_tFileInputExcel_1 = sheetList_tFileInputExcel_1
										.get(++sheetIndex_tFileInputExcel_1);
								currentRows_tFileInputExcel_1 = (sheet_tFileInputExcel_1.getLastRowNum() + 1);
							}
							globalMap.put("tFileInputExcel_1_CURRENT_SHEET", sheet_tFileInputExcel_1.getSheetName());
							if (rowCount_tFileInputExcel_1 <= i_tFileInputExcel_1) {
								row_tFileInputExcel_1 = sheet_tFileInputExcel_1
										.getRow(i_tFileInputExcel_1 - rowCount_tFileInputExcel_1);
							}
							row7 = null;
							int tempRowLength_tFileInputExcel_1 = 3;

							int columnIndex_tFileInputExcel_1 = 0;

							String[] temp_row_tFileInputExcel_1 = new String[tempRowLength_tFileInputExcel_1];
							int excel_end_column_tFileInputExcel_1;
							if (row_tFileInputExcel_1 == null) {
								excel_end_column_tFileInputExcel_1 = 0;
							} else {
								excel_end_column_tFileInputExcel_1 = row_tFileInputExcel_1.getLastCellNum();
							}
							int actual_end_column_tFileInputExcel_1;
							if (end_column_tFileInputExcel_1 == -1) {
								actual_end_column_tFileInputExcel_1 = excel_end_column_tFileInputExcel_1;
							} else {
								actual_end_column_tFileInputExcel_1 = end_column_tFileInputExcel_1 > excel_end_column_tFileInputExcel_1
										? excel_end_column_tFileInputExcel_1
										: end_column_tFileInputExcel_1;
							}
							org.apache.poi.ss.formula.eval.NumberEval ne_tFileInputExcel_1 = null;
							for (int i = 0; i < tempRowLength_tFileInputExcel_1; i++) {
								if (i + start_column_tFileInputExcel_1 < actual_end_column_tFileInputExcel_1) {
									org.apache.poi.ss.usermodel.Cell cell_tFileInputExcel_1 = row_tFileInputExcel_1
											.getCell(i + start_column_tFileInputExcel_1);
									if (cell_tFileInputExcel_1 != null) {
										switch (cell_tFileInputExcel_1.getCellType()) {
										case STRING:
											temp_row_tFileInputExcel_1[i] = cell_tFileInputExcel_1
													.getRichStringCellValue().getString();
											break;
										case NUMERIC:
											if (org.apache.poi.ss.usermodel.DateUtil
													.isCellDateFormatted(cell_tFileInputExcel_1)) {
												temp_row_tFileInputExcel_1[i] = cell_tFileInputExcel_1
														.getDateCellValue().toString();
											} else {
												temp_row_tFileInputExcel_1[i] = df_tFileInputExcel_1
														.format(cell_tFileInputExcel_1.getNumericCellValue());
											}
											break;
										case BOOLEAN:
											temp_row_tFileInputExcel_1[i] = String
													.valueOf(cell_tFileInputExcel_1.getBooleanCellValue());
											break;
										case FORMULA:
											switch (cell_tFileInputExcel_1.getCachedFormulaResultType()) {
											case STRING:
												temp_row_tFileInputExcel_1[i] = cell_tFileInputExcel_1
														.getRichStringCellValue().getString();
												break;
											case NUMERIC:
												if (org.apache.poi.ss.usermodel.DateUtil
														.isCellDateFormatted(cell_tFileInputExcel_1)) {
													temp_row_tFileInputExcel_1[i] = cell_tFileInputExcel_1
															.getDateCellValue().toString();
												} else {
													ne_tFileInputExcel_1 = new org.apache.poi.ss.formula.eval.NumberEval(
															cell_tFileInputExcel_1.getNumericCellValue());
													temp_row_tFileInputExcel_1[i] = ne_tFileInputExcel_1
															.getStringValue();
												}
												break;
											case BOOLEAN:
												temp_row_tFileInputExcel_1[i] = String
														.valueOf(cell_tFileInputExcel_1.getBooleanCellValue());
												break;
											default:
												temp_row_tFileInputExcel_1[i] = "";
											}
											break;
										default:
											temp_row_tFileInputExcel_1[i] = "";
										}
									} else {
										temp_row_tFileInputExcel_1[i] = "";
									}

								} else {
									temp_row_tFileInputExcel_1[i] = "";
								}
							}
							boolean whetherReject_tFileInputExcel_1 = false;
							row7 = new row7Struct();
							int curColNum_tFileInputExcel_1 = -1;
							String curColName_tFileInputExcel_1 = "";
							try {
								columnIndex_tFileInputExcel_1 = 0;

								if (temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1].length() > 0) {
									curColNum_tFileInputExcel_1 = columnIndex_tFileInputExcel_1
											+ start_column_tFileInputExcel_1 + 1;
									curColName_tFileInputExcel_1 = "Region";

									row7.Region = temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1];
								} else {
									row7.Region = null;
									emptyColumnCount_tFileInputExcel_1++;
								}
								columnIndex_tFileInputExcel_1 = 1;

								if (temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1].length() > 0) {
									curColNum_tFileInputExcel_1 = columnIndex_tFileInputExcel_1
											+ start_column_tFileInputExcel_1 + 1;
									curColName_tFileInputExcel_1 = "Regional_Manager";

									row7.Regional_Manager = temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1];
								} else {
									row7.Regional_Manager = null;
									emptyColumnCount_tFileInputExcel_1++;
								}
								columnIndex_tFileInputExcel_1 = 2;

								if (temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1].length() > 0) {
									curColNum_tFileInputExcel_1 = columnIndex_tFileInputExcel_1
											+ start_column_tFileInputExcel_1 + 1;
									curColName_tFileInputExcel_1 = "Yearly_Target";

									row7.Yearly_Target = ParserUtils.parseTo_Integer(ParserUtils.parseTo_Number(
											temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1], null,
											'.' == decimalChar_tFileInputExcel_1 ? null
													: decimalChar_tFileInputExcel_1));
								} else {
									row7.Yearly_Target = null;
									emptyColumnCount_tFileInputExcel_1++;
								}

								nb_line_tFileInputExcel_1++;

							} catch (java.lang.Exception e) {
								globalMap.put("tFileInputExcel_1_ERROR_MESSAGE", e.getMessage());
								whetherReject_tFileInputExcel_1 = true;
								System.err.println(e.getMessage());
								row7 = null;
							}

							/**
							 * [tFileInputExcel_1 begin ] stop
							 */

							/**
							 * [tFileInputExcel_1 main ] start
							 */

							currentComponent = "tFileInputExcel_1";

							tos_count_tFileInputExcel_1++;

							/**
							 * [tFileInputExcel_1 main ] stop
							 */

							/**
							 * [tFileInputExcel_1 process_data_begin ] start
							 */

							currentComponent = "tFileInputExcel_1";

							/**
							 * [tFileInputExcel_1 process_data_begin ] stop
							 */
// Start of branch "row7"
							if (row7 != null) {

								/**
								 * [tMap_4 main ] start
								 */

								currentComponent = "tMap_4";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row7"

									);
								}

								boolean hasCasePrimitiveKeyWithNull_tMap_4 = false;

								// ###############################
								// # Input tables (lookups)
								boolean rejectedInnerJoin_tMap_4 = false;
								boolean mainRowRejected_tMap_4 = false;

								// ###############################
								{ // start of Var scope

									// ###############################
									// # Vars tables

									Var__tMap_4__Struct Var = Var__tMap_4;// ###############################
									// ###############################
									// # Output tables

									Out_Location3 = null;

// # Output table : 'Out_Location3'
									Out_Location3_tmp.manager_key = 0;
									Out_Location3_tmp.region = row7.Region;
									Out_Location3_tmp.manager_name = row7.Regional_Manager;
									Out_Location3_tmp.yearly_target = row7.Yearly_Target;
									Out_Location3 = Out_Location3_tmp;
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_4 = false;

								tos_count_tMap_4++;

								/**
								 * [tMap_4 main ] stop
								 */

								/**
								 * [tMap_4 process_data_begin ] start
								 */

								currentComponent = "tMap_4";

								/**
								 * [tMap_4 process_data_begin ] stop
								 */
// Start of branch "Out_Location3"
								if (Out_Location3 != null) {

									/**
									 * [tDBOutput_4 main ] start
									 */

									currentComponent = "tDBOutput_4";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "Out_Location3"

										);
									}

									whetherReject_tDBOutput_4 = false;
									if (Out_Location3.region == null) {
										pstmt_tDBOutput_4.setNull(1, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_4.setString(1, Out_Location3.region);
									}

									if (Out_Location3.manager_name == null) {
										pstmt_tDBOutput_4.setNull(2, java.sql.Types.VARCHAR);
									} else {
										pstmt_tDBOutput_4.setString(2, Out_Location3.manager_name);
									}

									if (Out_Location3.yearly_target == null) {
										pstmt_tDBOutput_4.setNull(3, java.sql.Types.INTEGER);
									} else {
										pstmt_tDBOutput_4.setInt(3, Out_Location3.yearly_target);
									}

									pstmt_tDBOutput_4.addBatch();
									nb_line_tDBOutput_4++;

									batchSizeCounter_tDBOutput_4++;

									if ((batchSize_tDBOutput_4 > 0)
											&& (batchSize_tDBOutput_4 <= batchSizeCounter_tDBOutput_4)) {
										try {
											int countSum_tDBOutput_4 = 0;

											for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
												countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0
														: countEach_tDBOutput_4);
											}
											rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

											insertedCount_tDBOutput_4 += countSum_tDBOutput_4;

											batchSizeCounter_tDBOutput_4 = 0;
										} catch (java.sql.BatchUpdateException e_tDBOutput_4) {
											globalMap.put("tDBOutput_4_ERROR_MESSAGE", e_tDBOutput_4.getMessage());
											java.sql.SQLException ne_tDBOutput_4 = e_tDBOutput_4.getNextException(),
													sqle_tDBOutput_4 = null;
											String errormessage_tDBOutput_4;
											if (ne_tDBOutput_4 != null) {
												// build new exception to provide the original cause
												sqle_tDBOutput_4 = new java.sql.SQLException(
														e_tDBOutput_4.getMessage() + "\ncaused by: "
																+ ne_tDBOutput_4.getMessage(),
														ne_tDBOutput_4.getSQLState(), ne_tDBOutput_4.getErrorCode(),
														ne_tDBOutput_4);
												errormessage_tDBOutput_4 = sqle_tDBOutput_4.getMessage();
											} else {
												errormessage_tDBOutput_4 = e_tDBOutput_4.getMessage();
											}

											int countSum_tDBOutput_4 = 0;
											for (int countEach_tDBOutput_4 : e_tDBOutput_4.getUpdateCounts()) {
												countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0
														: countEach_tDBOutput_4);
											}
											rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

											insertedCount_tDBOutput_4 += countSum_tDBOutput_4;

											System.err.println(errormessage_tDBOutput_4);

										}
									}

									commitCounter_tDBOutput_4++;
									if (commitEvery_tDBOutput_4 <= commitCounter_tDBOutput_4) {
										if ((batchSize_tDBOutput_4 > 0) && (batchSizeCounter_tDBOutput_4 > 0)) {
											try {
												int countSum_tDBOutput_4 = 0;

												for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
													countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0
															: countEach_tDBOutput_4);
												}
												rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

												insertedCount_tDBOutput_4 += countSum_tDBOutput_4;

												batchSizeCounter_tDBOutput_4 = 0;
											} catch (java.sql.BatchUpdateException e_tDBOutput_4) {
												globalMap.put("tDBOutput_4_ERROR_MESSAGE", e_tDBOutput_4.getMessage());
												java.sql.SQLException ne_tDBOutput_4 = e_tDBOutput_4.getNextException(),
														sqle_tDBOutput_4 = null;
												String errormessage_tDBOutput_4;
												if (ne_tDBOutput_4 != null) {
													// build new exception to provide the original cause
													sqle_tDBOutput_4 = new java.sql.SQLException(
															e_tDBOutput_4.getMessage() + "\ncaused by: "
																	+ ne_tDBOutput_4.getMessage(),
															ne_tDBOutput_4.getSQLState(), ne_tDBOutput_4.getErrorCode(),
															ne_tDBOutput_4);
													errormessage_tDBOutput_4 = sqle_tDBOutput_4.getMessage();
												} else {
													errormessage_tDBOutput_4 = e_tDBOutput_4.getMessage();
												}

												int countSum_tDBOutput_4 = 0;
												for (int countEach_tDBOutput_4 : e_tDBOutput_4.getUpdateCounts()) {
													countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0
															: countEach_tDBOutput_4);
												}
												rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

												insertedCount_tDBOutput_4 += countSum_tDBOutput_4;

												System.err.println(errormessage_tDBOutput_4);

											}
										}
										if (rowsToCommitCount_tDBOutput_4 != 0) {

										}
										conn_tDBOutput_4.commit();
										if (rowsToCommitCount_tDBOutput_4 != 0) {

											rowsToCommitCount_tDBOutput_4 = 0;
										}
										commitCounter_tDBOutput_4 = 0;
									}

									tos_count_tDBOutput_4++;

									/**
									 * [tDBOutput_4 main ] stop
									 */

									/**
									 * [tDBOutput_4 process_data_begin ] start
									 */

									currentComponent = "tDBOutput_4";

									/**
									 * [tDBOutput_4 process_data_begin ] stop
									 */

									/**
									 * [tDBOutput_4 process_data_end ] start
									 */

									currentComponent = "tDBOutput_4";

									/**
									 * [tDBOutput_4 process_data_end ] stop
									 */

								} // End of branch "Out_Location3"

								/**
								 * [tMap_4 process_data_end ] start
								 */

								currentComponent = "tMap_4";

								/**
								 * [tMap_4 process_data_end ] stop
								 */

							} // End of branch "row7"

							/**
							 * [tFileInputExcel_1 process_data_end ] start
							 */

							currentComponent = "tFileInputExcel_1";

							/**
							 * [tFileInputExcel_1 process_data_end ] stop
							 */

							/**
							 * [tFileInputExcel_1 end ] start
							 */

							currentComponent = "tFileInputExcel_1";

						}

						globalMap.put("tFileInputExcel_1_NB_LINE", nb_line_tFileInputExcel_1);

					}

				} finally {

					if (!(source_tFileInputExcel_1 instanceof java.io.InputStream)) {
						workbook_tFileInputExcel_1.getPackage().revert();
					}

				}

				ok_Hash.put("tFileInputExcel_1", true);
				end_Hash.put("tFileInputExcel_1", System.currentTimeMillis());

				/**
				 * [tFileInputExcel_1 end ] stop
				 */

				/**
				 * [tMap_4 end ] start
				 */

				currentComponent = "tMap_4";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row7");
				}

				ok_Hash.put("tMap_4", true);
				end_Hash.put("tMap_4", System.currentTimeMillis());

				/**
				 * [tMap_4 end ] stop
				 */

				/**
				 * [tDBOutput_4 end ] start
				 */

				currentComponent = "tDBOutput_4";

				try {
					int countSum_tDBOutput_4 = 0;
					if (pstmt_tDBOutput_4 != null && batchSizeCounter_tDBOutput_4 > 0) {

						for (int countEach_tDBOutput_4 : pstmt_tDBOutput_4.executeBatch()) {
							countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
						}
						rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

					}

					insertedCount_tDBOutput_4 += countSum_tDBOutput_4;

				} catch (java.sql.BatchUpdateException e_tDBOutput_4) {
					globalMap.put("tDBOutput_4_ERROR_MESSAGE", e_tDBOutput_4.getMessage());
					java.sql.SQLException ne_tDBOutput_4 = e_tDBOutput_4.getNextException(), sqle_tDBOutput_4 = null;
					String errormessage_tDBOutput_4;
					if (ne_tDBOutput_4 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_4 = new java.sql.SQLException(
								e_tDBOutput_4.getMessage() + "\ncaused by: " + ne_tDBOutput_4.getMessage(),
								ne_tDBOutput_4.getSQLState(), ne_tDBOutput_4.getErrorCode(), ne_tDBOutput_4);
						errormessage_tDBOutput_4 = sqle_tDBOutput_4.getMessage();
					} else {
						errormessage_tDBOutput_4 = e_tDBOutput_4.getMessage();
					}

					int countSum_tDBOutput_4 = 0;
					for (int countEach_tDBOutput_4 : e_tDBOutput_4.getUpdateCounts()) {
						countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
					}
					rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;

					insertedCount_tDBOutput_4 += countSum_tDBOutput_4;

					System.err.println(errormessage_tDBOutput_4);

				}

				if (pstmt_tDBOutput_4 != null) {

					pstmt_tDBOutput_4.close();
					resourceMap.remove("pstmt_tDBOutput_4");
				}
				resourceMap.put("statementClosed_tDBOutput_4", true);
				if (rowsToCommitCount_tDBOutput_4 != 0) {

				}
				conn_tDBOutput_4.commit();
				if (rowsToCommitCount_tDBOutput_4 != 0) {

					rowsToCommitCount_tDBOutput_4 = 0;
				}
				commitCounter_tDBOutput_4 = 0;

				conn_tDBOutput_4.close();

				resourceMap.put("finish_tDBOutput_4", true);

				nb_line_deleted_tDBOutput_4 = nb_line_deleted_tDBOutput_4 + deletedCount_tDBOutput_4;
				nb_line_update_tDBOutput_4 = nb_line_update_tDBOutput_4 + updatedCount_tDBOutput_4;
				nb_line_inserted_tDBOutput_4 = nb_line_inserted_tDBOutput_4 + insertedCount_tDBOutput_4;
				nb_line_rejected_tDBOutput_4 = nb_line_rejected_tDBOutput_4 + rejectedCount_tDBOutput_4;

				globalMap.put("tDBOutput_4_NB_LINE", nb_line_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_UPDATED", nb_line_update_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_DELETED", nb_line_deleted_tDBOutput_4);
				globalMap.put("tDBOutput_4_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_4);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "Out_Location3");
				}

				ok_Hash.put("tDBOutput_4", true);
				end_Hash.put("tDBOutput_4", System.currentTimeMillis());

				/**
				 * [tDBOutput_4 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tFileInputExcel_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk4", 0, "ok");
			}

			tFileInputDelimited_4Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputExcel_1 finally ] start
				 */

				currentComponent = "tFileInputExcel_1";

				/**
				 * [tFileInputExcel_1 finally ] stop
				 */

				/**
				 * [tMap_4 finally ] start
				 */

				currentComponent = "tMap_4";

				/**
				 * [tMap_4 finally ] stop
				 */

				/**
				 * [tDBOutput_4 finally ] start
				 */

				currentComponent = "tDBOutput_4";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_4") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_4 = null;
						if ((pstmtToClose_tDBOutput_4 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_4")) != null) {
							pstmtToClose_tDBOutput_4.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_4") == null) {
						java.sql.Connection ctn_tDBOutput_4 = null;
						if ((ctn_tDBOutput_4 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_4")) != null) {
							try {
								ctn_tDBOutput_4.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_4) {
								String errorMessage_tDBOutput_4 = "failed to close the connection in tDBOutput_4 :"
										+ sqlEx_tDBOutput_4.getMessage();
								System.err.println(errorMessage_tDBOutput_4);
							}
						}
					}
				}

				/**
				 * [tDBOutput_4 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputExcel_1_SUBPROCESS_STATE", 1);
	}

	public static class Out_LocationFStruct implements routines.system.IPersistableRow<Out_LocationFStruct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int fact_id;

		public int getFact_id() {
			return this.fact_id;
		}

		public Integer location_key;

		public Integer getLocation_key() {
			return this.location_key;
		}

		public Integer product_key;

		public Integer getProduct_key() {
			return this.product_key;
		}

		public Integer customer_key;

		public Integer getCustomer_key() {
			return this.customer_key;
		}

		public Integer manager_key;

		public Integer getManager_key() {
			return this.manager_key;
		}

		public String order_id;

		public String getOrder_id() {
			return this.order_id;
		}

		public java.util.Date order_date;

		public java.util.Date getOrder_date() {
			return this.order_date;
		}

		public java.util.Date ship_date;

		public java.util.Date getShip_date() {
			return this.ship_date;
		}

		public String ship_mode;

		public String getShip_mode() {
			return this.ship_mode;
		}

		public Float sales;

		public Float getSales() {
			return this.sales;
		}

		public Integer quantity;

		public Integer getQuantity() {
			return this.quantity;
		}

		public Float discount;

		public Float getDiscount() {
			return this.discount;
		}

		public Double profit;

		public Double getProfit() {
			return this.profit;
		}

		public Float shipping_cost;

		public Float getShipping_cost() {
			return this.shipping_cost;
		}

		public Boolean is_returned;

		public Boolean getIs_returned() {
			return this.is_returned;
		}

		public String return_reason;

		public String getReturn_reason() {
			return this.return_reason;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.fact_id;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Out_LocationFStruct other = (Out_LocationFStruct) obj;

			if (this.fact_id != other.fact_id)
				return false;

			return true;
		}

		public void copyDataTo(Out_LocationFStruct other) {

			other.fact_id = this.fact_id;
			other.location_key = this.location_key;
			other.product_key = this.product_key;
			other.customer_key = this.customer_key;
			other.manager_key = this.manager_key;
			other.order_id = this.order_id;
			other.order_date = this.order_date;
			other.ship_date = this.ship_date;
			other.ship_mode = this.ship_mode;
			other.sales = this.sales;
			other.quantity = this.quantity;
			other.discount = this.discount;
			other.profit = this.profit;
			other.shipping_cost = this.shipping_cost;
			other.is_returned = this.is_returned;
			other.return_reason = this.return_reason;

		}

		public void copyKeysDataTo(Out_LocationFStruct other) {

			other.fact_id = this.fact_id;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.fact_id = dis.readInt();

					this.location_key = readInteger(dis);

					this.product_key = readInteger(dis);

					this.customer_key = readInteger(dis);

					this.manager_key = readInteger(dis);

					this.order_id = readString(dis);

					this.order_date = readDate(dis);

					this.ship_date = readDate(dis);

					this.ship_mode = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.sales = null;
					} else {
						this.sales = dis.readFloat();
					}

					this.quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.discount = null;
					} else {
						this.discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.profit = null;
					} else {
						this.profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.shipping_cost = null;
					} else {
						this.shipping_cost = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.is_returned = null;
					} else {
						this.is_returned = dis.readBoolean();
					}

					this.return_reason = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.fact_id = dis.readInt();

					this.location_key = readInteger(dis);

					this.product_key = readInteger(dis);

					this.customer_key = readInteger(dis);

					this.manager_key = readInteger(dis);

					this.order_id = readString(dis);

					this.order_date = readDate(dis);

					this.ship_date = readDate(dis);

					this.ship_mode = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.sales = null;
					} else {
						this.sales = dis.readFloat();
					}

					this.quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.discount = null;
					} else {
						this.discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.profit = null;
					} else {
						this.profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.shipping_cost = null;
					} else {
						this.shipping_cost = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.is_returned = null;
					} else {
						this.is_returned = dis.readBoolean();
					}

					this.return_reason = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.fact_id);

				// Integer

				writeInteger(this.location_key, dos);

				// Integer

				writeInteger(this.product_key, dos);

				// Integer

				writeInteger(this.customer_key, dos);

				// Integer

				writeInteger(this.manager_key, dos);

				// String

				writeString(this.order_id, dos);

				// java.util.Date

				writeDate(this.order_date, dos);

				// java.util.Date

				writeDate(this.ship_date, dos);

				// String

				writeString(this.ship_mode, dos);

				// Float

				if (this.sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.sales);
				}

				// Integer

				writeInteger(this.quantity, dos);

				// Float

				if (this.discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.discount);
				}

				// Double

				if (this.profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.profit);
				}

				// Float

				if (this.shipping_cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.shipping_cost);
				}

				// Boolean

				if (this.is_returned == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.is_returned);
				}

				// String

				writeString(this.return_reason, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.fact_id);

				// Integer

				writeInteger(this.location_key, dos);

				// Integer

				writeInteger(this.product_key, dos);

				// Integer

				writeInteger(this.customer_key, dos);

				// Integer

				writeInteger(this.manager_key, dos);

				// String

				writeString(this.order_id, dos);

				// java.util.Date

				writeDate(this.order_date, dos);

				// java.util.Date

				writeDate(this.ship_date, dos);

				// String

				writeString(this.ship_mode, dos);

				// Float

				if (this.sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.sales);
				}

				// Integer

				writeInteger(this.quantity, dos);

				// Float

				if (this.discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.discount);
				}

				// Double

				if (this.profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.profit);
				}

				// Float

				if (this.shipping_cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.shipping_cost);
				}

				// Boolean

				if (this.is_returned == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeBoolean(this.is_returned);
				}

				// String

				writeString(this.return_reason, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("fact_id=" + String.valueOf(fact_id));
			sb.append(",location_key=" + String.valueOf(location_key));
			sb.append(",product_key=" + String.valueOf(product_key));
			sb.append(",customer_key=" + String.valueOf(customer_key));
			sb.append(",manager_key=" + String.valueOf(manager_key));
			sb.append(",order_id=" + order_id);
			sb.append(",order_date=" + String.valueOf(order_date));
			sb.append(",ship_date=" + String.valueOf(ship_date));
			sb.append(",ship_mode=" + ship_mode);
			sb.append(",sales=" + String.valueOf(sales));
			sb.append(",quantity=" + String.valueOf(quantity));
			sb.append(",discount=" + String.valueOf(discount));
			sb.append(",profit=" + String.valueOf(profit));
			sb.append(",shipping_cost=" + String.valueOf(shipping_cost));
			sb.append(",is_returned=" + String.valueOf(is_returned));
			sb.append(",return_reason=" + return_reason);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(Out_LocationFStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.fact_id, other.fact_id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row_MainStruct implements routines.system.IPersistableRow<row_MainStruct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];

		public Integer Row_ID;

		public Integer getRow_ID() {
			return this.Row_ID;
		}

		public String Order_ID;

		public String getOrder_ID() {
			return this.Order_ID;
		}

		public java.util.Date Order_Date;

		public java.util.Date getOrder_Date() {
			return this.Order_Date;
		}

		public java.util.Date Ship_Date;

		public java.util.Date getShip_Date() {
			return this.Ship_Date;
		}

		public String Ship_Mode;

		public String getShip_Mode() {
			return this.Ship_Mode;
		}

		public String Customer_ID;

		public String getCustomer_ID() {
			return this.Customer_ID;
		}

		public String Customer_Name;

		public String getCustomer_Name() {
			return this.Customer_Name;
		}

		public String Segment;

		public String getSegment() {
			return this.Segment;
		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public String State;

		public String getState() {
			return this.State;
		}

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Postal_Code;

		public String getPostal_Code() {
			return this.Postal_Code;
		}

		public String Market;

		public String getMarket() {
			return this.Market;
		}

		public String Region;

		public String getRegion() {
			return this.Region;
		}

		public String Product_ID;

		public String getProduct_ID() {
			return this.Product_ID;
		}

		public String Category;

		public String getCategory() {
			return this.Category;
		}

		public String Sub_Category;

		public String getSub_Category() {
			return this.Sub_Category;
		}

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Float Sales;

		public Float getSales() {
			return this.Sales;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public Float Discount;

		public Float getDiscount() {
			return this.Discount;
		}

		public Double Profit;

		public Double getProfit() {
			return this.Profit;
		}

		public Float Shipping_Cost;

		public Float getShipping_Cost() {
			return this.Shipping_Cost;
		}

		public String Order_Priority;

		public String getOrder_Priority() {
			return this.Order_Priority;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Row_ID=" + String.valueOf(Row_ID));
			sb.append(",Order_ID=" + Order_ID);
			sb.append(",Order_Date=" + String.valueOf(Order_Date));
			sb.append(",Ship_Date=" + String.valueOf(Ship_Date));
			sb.append(",Ship_Mode=" + Ship_Mode);
			sb.append(",Customer_ID=" + Customer_ID);
			sb.append(",Customer_Name=" + Customer_Name);
			sb.append(",Segment=" + Segment);
			sb.append(",City=" + City);
			sb.append(",State=" + State);
			sb.append(",Country=" + Country);
			sb.append(",Postal_Code=" + Postal_Code);
			sb.append(",Market=" + Market);
			sb.append(",Region=" + Region);
			sb.append(",Product_ID=" + Product_ID);
			sb.append(",Category=" + Category);
			sb.append(",Sub_Category=" + Sub_Category);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Sales=" + String.valueOf(Sales));
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",Discount=" + String.valueOf(Discount));
			sb.append(",Profit=" + String.valueOf(Profit));
			sb.append(",Shipping_Cost=" + String.valueOf(Shipping_Cost));
			sb.append(",Order_Priority=" + Order_Priority);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row_MainStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class after_tFileInputDelimited_4Struct
			implements routines.system.IPersistableRow<after_tFileInputDelimited_4Struct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer Row_ID;

		public Integer getRow_ID() {
			return this.Row_ID;
		}

		public String Order_ID;

		public String getOrder_ID() {
			return this.Order_ID;
		}

		public java.util.Date Order_Date;

		public java.util.Date getOrder_Date() {
			return this.Order_Date;
		}

		public java.util.Date Ship_Date;

		public java.util.Date getShip_Date() {
			return this.Ship_Date;
		}

		public String Ship_Mode;

		public String getShip_Mode() {
			return this.Ship_Mode;
		}

		public String Customer_ID;

		public String getCustomer_ID() {
			return this.Customer_ID;
		}

		public String Customer_Name;

		public String getCustomer_Name() {
			return this.Customer_Name;
		}

		public String Segment;

		public String getSegment() {
			return this.Segment;
		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public String State;

		public String getState() {
			return this.State;
		}

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Postal_Code;

		public String getPostal_Code() {
			return this.Postal_Code;
		}

		public String Market;

		public String getMarket() {
			return this.Market;
		}

		public String Region;

		public String getRegion() {
			return this.Region;
		}

		public String Product_ID;

		public String getProduct_ID() {
			return this.Product_ID;
		}

		public String Category;

		public String getCategory() {
			return this.Category;
		}

		public String Sub_Category;

		public String getSub_Category() {
			return this.Sub_Category;
		}

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public Float Sales;

		public Float getSales() {
			return this.Sales;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public Float Discount;

		public Float getDiscount() {
			return this.Discount;
		}

		public Double Profit;

		public Double getProfit() {
			return this.Profit;
		}

		public Float Shipping_Cost;

		public Float getShipping_Cost() {
			return this.Shipping_Cost;
		}

		public String Order_Priority;

		public String getOrder_Priority() {
			return this.Order_Priority;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Row_ID == null) ? 0 : this.Row_ID.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final after_tFileInputDelimited_4Struct other = (after_tFileInputDelimited_4Struct) obj;

			if (this.Row_ID == null) {
				if (other.Row_ID != null)
					return false;

			} else if (!this.Row_ID.equals(other.Row_ID))

				return false;

			return true;
		}

		public void copyDataTo(after_tFileInputDelimited_4Struct other) {

			other.Row_ID = this.Row_ID;
			other.Order_ID = this.Order_ID;
			other.Order_Date = this.Order_Date;
			other.Ship_Date = this.Ship_Date;
			other.Ship_Mode = this.Ship_Mode;
			other.Customer_ID = this.Customer_ID;
			other.Customer_Name = this.Customer_Name;
			other.Segment = this.Segment;
			other.City = this.City;
			other.State = this.State;
			other.Country = this.Country;
			other.Postal_Code = this.Postal_Code;
			other.Market = this.Market;
			other.Region = this.Region;
			other.Product_ID = this.Product_ID;
			other.Category = this.Category;
			other.Sub_Category = this.Sub_Category;
			other.Product_Name = this.Product_Name;
			other.Sales = this.Sales;
			other.Quantity = this.Quantity;
			other.Discount = this.Discount;
			other.Profit = this.Profit;
			other.Shipping_Cost = this.Shipping_Cost;
			other.Order_Priority = this.Order_Priority;

		}

		public void copyKeysDataTo(after_tFileInputDelimited_4Struct other) {

			other.Row_ID = this.Row_ID;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.Row_ID = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Date = readDate(dis);

					this.Ship_Date = readDate(dis);

					this.Ship_Mode = readString(dis);

					this.Customer_ID = readString(dis);

					this.Customer_Name = readString(dis);

					this.Segment = readString(dis);

					this.City = readString(dis);

					this.State = readString(dis);

					this.Country = readString(dis);

					this.Postal_Code = readString(dis);

					this.Market = readString(dis);

					this.Region = readString(dis);

					this.Product_ID = readString(dis);

					this.Category = readString(dis);

					this.Sub_Category = readString(dis);

					this.Product_Name = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Sales = null;
					} else {
						this.Sales = dis.readFloat();
					}

					this.Quantity = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Discount = null;
					} else {
						this.Discount = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Profit = null;
					} else {
						this.Profit = dis.readDouble();
					}

					length = dis.readByte();
					if (length == -1) {
						this.Shipping_Cost = null;
					} else {
						this.Shipping_Cost = dis.readFloat();
					}

					this.Order_Priority = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Row_ID, dos);

				// String

				writeString(this.Order_ID, dos);

				// java.util.Date

				writeDate(this.Order_Date, dos);

				// java.util.Date

				writeDate(this.Ship_Date, dos);

				// String

				writeString(this.Ship_Mode, dos);

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Customer_Name, dos);

				// String

				writeString(this.Segment, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.State, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Postal_Code, dos);

				// String

				writeString(this.Market, dos);

				// String

				writeString(this.Region, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Category, dos);

				// String

				writeString(this.Sub_Category, dos);

				// String

				writeString(this.Product_Name, dos);

				// Float

				if (this.Sales == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Sales);
				}

				// Integer

				writeInteger(this.Quantity, dos);

				// Float

				if (this.Discount == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Discount);
				}

				// Double

				if (this.Profit == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeDouble(this.Profit);
				}

				// Float

				if (this.Shipping_Cost == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.Shipping_Cost);
				}

				// String

				writeString(this.Order_Priority, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Row_ID=" + String.valueOf(Row_ID));
			sb.append(",Order_ID=" + Order_ID);
			sb.append(",Order_Date=" + String.valueOf(Order_Date));
			sb.append(",Ship_Date=" + String.valueOf(Ship_Date));
			sb.append(",Ship_Mode=" + Ship_Mode);
			sb.append(",Customer_ID=" + Customer_ID);
			sb.append(",Customer_Name=" + Customer_Name);
			sb.append(",Segment=" + Segment);
			sb.append(",City=" + City);
			sb.append(",State=" + State);
			sb.append(",Country=" + Country);
			sb.append(",Postal_Code=" + Postal_Code);
			sb.append(",Market=" + Market);
			sb.append(",Region=" + Region);
			sb.append(",Product_ID=" + Product_ID);
			sb.append(",Category=" + Category);
			sb.append(",Sub_Category=" + Sub_Category);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Sales=" + String.valueOf(Sales));
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",Discount=" + String.valueOf(Discount));
			sb.append(",Profit=" + String.valueOf(Profit));
			sb.append(",Shipping_Cost=" + String.valueOf(Shipping_Cost));
			sb.append(",Order_Priority=" + Order_Priority);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tFileInputDelimited_4Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Row_ID, other.Row_ID);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_4_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				tDBInput_1Process(globalMap);
				tDBInput_2Process(globalMap);
				tDBInput_3Process(globalMap);
				tDBInput_4Process(globalMap);
				tFileInputXML_1Process(globalMap);

				row_MainStruct row_Main = new row_MainStruct();
				Out_LocationFStruct Out_LocationF = new Out_LocationFStruct();

				/**
				 * [tDBOutput_5 begin ] start
				 */

				ok_Hash.put("tDBOutput_5", false);
				start_Hash.put("tDBOutput_5", System.currentTimeMillis());

				currentComponent = "tDBOutput_5";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "Out_LocationF");
				}

				int tos_count_tDBOutput_5 = 0;

				String dbschema_tDBOutput_5 = null;
				dbschema_tDBOutput_5 = "public";

				String tableName_tDBOutput_5 = null;
				if (dbschema_tDBOutput_5 == null || dbschema_tDBOutput_5.trim().length() == 0) {
					tableName_tDBOutput_5 = ("fact_sales");
				} else {
					tableName_tDBOutput_5 = dbschema_tDBOutput_5 + "\".\"" + ("fact_sales");
				}

				int nb_line_tDBOutput_5 = 0;
				int nb_line_update_tDBOutput_5 = 0;
				int nb_line_inserted_tDBOutput_5 = 0;
				int nb_line_deleted_tDBOutput_5 = 0;
				int nb_line_rejected_tDBOutput_5 = 0;

				int deletedCount_tDBOutput_5 = 0;
				int updatedCount_tDBOutput_5 = 0;
				int insertedCount_tDBOutput_5 = 0;
				int rowsToCommitCount_tDBOutput_5 = 0;
				int rejectedCount_tDBOutput_5 = 0;

				boolean whetherReject_tDBOutput_5 = false;

				java.sql.Connection conn_tDBOutput_5 = null;
				String dbUser_tDBOutput_5 = null;

				java.lang.Class.forName("org.postgresql.Driver");

				String url_tDBOutput_5 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "GlobalRetailDW";
				dbUser_tDBOutput_5 = "postgres";

				final String decryptedPassword_tDBOutput_5 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:2MR/4ZLb2XrDybW0KlRupgGa8tT1XuO37f7vSD9yNETxHQ+0bA==");

				String dbPwd_tDBOutput_5 = decryptedPassword_tDBOutput_5;

				conn_tDBOutput_5 = java.sql.DriverManager.getConnection(url_tDBOutput_5, dbUser_tDBOutput_5,
						dbPwd_tDBOutput_5);

				resourceMap.put("conn_tDBOutput_5", conn_tDBOutput_5);
				conn_tDBOutput_5.setAutoCommit(false);
				int commitEvery_tDBOutput_5 = 10000;
				int commitCounter_tDBOutput_5 = 0;

				int batchSize_tDBOutput_5 = 10000;
				int batchSizeCounter_tDBOutput_5 = 0;

				int count_tDBOutput_5 = 0;
				String insert_tDBOutput_5 = "INSERT INTO \"" + tableName_tDBOutput_5
						+ "\" (\"location_key\",\"product_key\",\"customer_key\",\"manager_key\",\"order_id\",\"order_date\",\"ship_date\",\"ship_mode\",\"sales\",\"quantity\",\"discount\",\"profit\",\"shipping_cost\",\"is_returned\",\"return_reason\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				java.sql.PreparedStatement pstmt_tDBOutput_5 = conn_tDBOutput_5.prepareStatement(insert_tDBOutput_5);
				resourceMap.put("pstmt_tDBOutput_5", pstmt_tDBOutput_5);

				/**
				 * [tDBOutput_5 begin ] stop
				 */

				/**
				 * [tMap_5 begin ] start
				 */

				ok_Hash.put("tMap_5", false);
				start_Hash.put("tMap_5", System.currentTimeMillis());

				currentComponent = "tMap_5";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row_Main");
				}

				int tos_count_tMap_5 = 0;

// ###############################
// # Lookup's keys initialization

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_XMLStruct> tHash_Lookup_row_XML = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_XMLStruct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_XMLStruct>) globalMap
						.get("tHash_Lookup_row_XML"));

				row_XMLStruct row_XMLHashKey = new row_XMLStruct();
				row_XMLStruct row_XMLDefault = new row_XMLStruct();

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_LocStruct> tHash_Lookup_row_Loc = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_LocStruct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_LocStruct>) globalMap
						.get("tHash_Lookup_row_Loc"));

				row_LocStruct row_LocHashKey = new row_LocStruct();
				row_LocStruct row_LocDefault = new row_LocStruct();

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_ProdStruct> tHash_Lookup_row_Prod = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_ProdStruct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_ProdStruct>) globalMap
						.get("tHash_Lookup_row_Prod"));

				row_ProdStruct row_ProdHashKey = new row_ProdStruct();
				row_ProdStruct row_ProdDefault = new row_ProdStruct();

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_CustStruct> tHash_Lookup_row_Cust = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_CustStruct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_CustStruct>) globalMap
						.get("tHash_Lookup_row_Cust"));

				row_CustStruct row_CustHashKey = new row_CustStruct();
				row_CustStruct row_CustDefault = new row_CustStruct();

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_MgrStruct> tHash_Lookup_row_Mgr = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_MgrStruct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_MgrStruct>) globalMap
						.get("tHash_Lookup_row_Mgr"));

				row_MgrStruct row_MgrHashKey = new row_MgrStruct();
				row_MgrStruct row_MgrDefault = new row_MgrStruct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_5__Struct {
				}
				Var__tMap_5__Struct Var__tMap_5 = new Var__tMap_5__Struct();
// ###############################

// ###############################
// # Outputs initialization
				Out_LocationFStruct Out_LocationF_tmp = new Out_LocationFStruct();
// ###############################

				/**
				 * [tMap_5 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_4 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_4", false);
				start_Hash.put("tFileInputDelimited_4", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_4";

				int tos_count_tFileInputDelimited_4 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_4 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_4 = 0;
				int footer_tFileInputDelimited_4 = 0;
				int totalLinetFileInputDelimited_4 = 0;
				int limittFileInputDelimited_4 = -1;
				int lastLinetFileInputDelimited_4 = -1;

				char fieldSeparator_tFileInputDelimited_4[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ";").length() > 0) {
					fieldSeparator_tFileInputDelimited_4 = ((String) ";").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_4[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_4 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_4 = /** Start field tFileInputDelimited_4:FILENAME */
						"C:/Users/DELL/Desktop/GlobalRetail_BI_360/data/generated_sources/Source_ERP_Ventes.csv"/**
																												 * End
																												 * field
																												 * tFileInputDelimited_4:FILENAME
																												 */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_4 = null;

				try {

					String[] rowtFileInputDelimited_4 = null;
					int currentLinetFileInputDelimited_4 = 0;
					int outputLinetFileInputDelimited_4 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_4 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_4 = 0;
							if (footer_value_tFileInputDelimited_4 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_4 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_4,
									fieldSeparator_tFileInputDelimited_4[0], "UTF-8");
						} else {
							csvReadertFileInputDelimited_4 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_4),
									fieldSeparator_tFileInputDelimited_4[0], "UTF-8");
						}

						csvReadertFileInputDelimited_4.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_4[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_4[0] != '\r'))
							csvReadertFileInputDelimited_4.setLineEnd("" + rowSeparator_tFileInputDelimited_4[0]);

						csvReadertFileInputDelimited_4.setQuoteChar('"');

						csvReadertFileInputDelimited_4.setEscapeChar(csvReadertFileInputDelimited_4.getQuoteChar());

						if (footer_tFileInputDelimited_4 > 0) {
							for (totalLinetFileInputDelimited_4 = 0; totalLinetFileInputDelimited_4 < 1; totalLinetFileInputDelimited_4++) {
								csvReadertFileInputDelimited_4.readNext();
							}
							csvReadertFileInputDelimited_4.setSkipEmptyRecords(false);
							while (csvReadertFileInputDelimited_4.readNext()) {

								totalLinetFileInputDelimited_4++;

							}
							int lastLineTemptFileInputDelimited_4 = totalLinetFileInputDelimited_4
									- footer_tFileInputDelimited_4 < 0 ? 0
											: totalLinetFileInputDelimited_4 - footer_tFileInputDelimited_4;
							if (lastLinetFileInputDelimited_4 > 0) {
								lastLinetFileInputDelimited_4 = lastLinetFileInputDelimited_4 < lastLineTemptFileInputDelimited_4
										? lastLinetFileInputDelimited_4
										: lastLineTemptFileInputDelimited_4;
							} else {
								lastLinetFileInputDelimited_4 = lastLineTemptFileInputDelimited_4;
							}

							csvReadertFileInputDelimited_4.close();
							if (filename_tFileInputDelimited_4 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_4 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_4,
										fieldSeparator_tFileInputDelimited_4[0], "UTF-8");
							} else {
								csvReadertFileInputDelimited_4 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_4),
										fieldSeparator_tFileInputDelimited_4[0], "UTF-8");
							}
							csvReadertFileInputDelimited_4.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_4[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_4[0] != '\r'))
								csvReadertFileInputDelimited_4.setLineEnd("" + rowSeparator_tFileInputDelimited_4[0]);

							csvReadertFileInputDelimited_4.setQuoteChar('"');

							csvReadertFileInputDelimited_4.setEscapeChar(csvReadertFileInputDelimited_4.getQuoteChar());

						}

						if (limittFileInputDelimited_4 != 0) {
							for (currentLinetFileInputDelimited_4 = 0; currentLinetFileInputDelimited_4 < 1; currentLinetFileInputDelimited_4++) {
								csvReadertFileInputDelimited_4.readNext();
							}
						}
						csvReadertFileInputDelimited_4.setSkipEmptyRecords(false);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					while (limittFileInputDelimited_4 != 0 && csvReadertFileInputDelimited_4 != null
							&& csvReadertFileInputDelimited_4.readNext()) {
						rowstate_tFileInputDelimited_4.reset();

						rowtFileInputDelimited_4 = csvReadertFileInputDelimited_4.getValues();

						currentLinetFileInputDelimited_4++;

						if (lastLinetFileInputDelimited_4 > -1
								&& currentLinetFileInputDelimited_4 > lastLinetFileInputDelimited_4) {
							break;
						}
						outputLinetFileInputDelimited_4++;
						if (limittFileInputDelimited_4 > 0
								&& outputLinetFileInputDelimited_4 > limittFileInputDelimited_4) {
							break;
						}

						row_Main = null;

						boolean whetherReject_tFileInputDelimited_4 = false;
						row_Main = new row_MainStruct();
						try {

							char fieldSeparator_tFileInputDelimited_4_ListType[] = null;
							// support passing value (property: Field Separator) by 'context.fs' or
							// 'globalMap.get("fs")'.
							if (((String) ";").length() > 0) {
								fieldSeparator_tFileInputDelimited_4_ListType = ((String) ";").toCharArray();
							} else {
								throw new IllegalArgumentException("Field Separator must be assigned a char.");
							}
							if (rowtFileInputDelimited_4.length == 1 && ("\015").equals(rowtFileInputDelimited_4[0])) {// empty
																														// line
																														// when
																														// row
																														// separator
																														// is
																														// '\n'

								row_Main.Row_ID = null;

								row_Main.Order_ID = null;

								row_Main.Order_Date = null;

								row_Main.Ship_Date = null;

								row_Main.Ship_Mode = null;

								row_Main.Customer_ID = null;

								row_Main.Customer_Name = null;

								row_Main.Segment = null;

								row_Main.City = null;

								row_Main.State = null;

								row_Main.Country = null;

								row_Main.Postal_Code = null;

								row_Main.Market = null;

								row_Main.Region = null;

								row_Main.Product_ID = null;

								row_Main.Category = null;

								row_Main.Sub_Category = null;

								row_Main.Product_Name = null;

								row_Main.Sales = null;

								row_Main.Quantity = null;

								row_Main.Discount = null;

								row_Main.Profit = null;

								row_Main.Shipping_Cost = null;

								row_Main.Order_Priority = null;

							} else {

								int columnIndexWithD_tFileInputDelimited_4 = 0; // Column Index

								columnIndexWithD_tFileInputDelimited_4 = 0;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									if (rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4].length() > 0) {
										try {

											row_Main.Row_ID = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4]);

										} catch (java.lang.Exception ex_tFileInputDelimited_4) {
											globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
													ex_tFileInputDelimited_4.getMessage());
											rowstate_tFileInputDelimited_4.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Row_ID", "row_Main",
															rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4],
															ex_tFileInputDelimited_4),
													ex_tFileInputDelimited_4));
										}
									} else {

										row_Main.Row_ID = null;

									}

								} else {

									row_Main.Row_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 1;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Order_ID = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Order_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 2;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									if (rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4].length() > 0) {
										try {

											row_Main.Order_Date = ParserUtils.parseTo_Date(
													rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4],
													"dd-MM-yyyy");

										} catch (java.lang.Exception ex_tFileInputDelimited_4) {
											globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
													ex_tFileInputDelimited_4.getMessage());
											rowstate_tFileInputDelimited_4.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Order_Date", "row_Main",
															rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4],
															ex_tFileInputDelimited_4),
													ex_tFileInputDelimited_4));
										}
									} else {

										row_Main.Order_Date = null;

									}

								} else {

									row_Main.Order_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 3;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									if (rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4].length() > 0) {
										try {

											row_Main.Ship_Date = ParserUtils.parseTo_Date(
													rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4],
													"dd-MM-yyyy");

										} catch (java.lang.Exception ex_tFileInputDelimited_4) {
											globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
													ex_tFileInputDelimited_4.getMessage());
											rowstate_tFileInputDelimited_4.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Ship_Date", "row_Main",
															rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4],
															ex_tFileInputDelimited_4),
													ex_tFileInputDelimited_4));
										}
									} else {

										row_Main.Ship_Date = null;

									}

								} else {

									row_Main.Ship_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 4;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Ship_Mode = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Ship_Mode = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 5;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Customer_ID = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Customer_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 6;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Customer_Name = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Customer_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 7;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Segment = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Segment = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 8;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.City = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.City = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 9;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.State = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.State = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 10;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Country = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Country = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 11;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Postal_Code = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Postal_Code = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 12;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Market = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Market = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 13;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Region = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Region = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 14;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Product_ID = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Product_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 15;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Category = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Category = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 16;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Sub_Category = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Sub_Category = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 17;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Product_Name = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Product_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 18;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									if (rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4].length() > 0) {
										try {

											row_Main.Sales = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4]);

										} catch (java.lang.Exception ex_tFileInputDelimited_4) {
											globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
													ex_tFileInputDelimited_4.getMessage());
											rowstate_tFileInputDelimited_4.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Sales", "row_Main",
															rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4],
															ex_tFileInputDelimited_4),
													ex_tFileInputDelimited_4));
										}
									} else {

										row_Main.Sales = null;

									}

								} else {

									row_Main.Sales = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 19;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									if (rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4].length() > 0) {
										try {

											row_Main.Quantity = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4]);

										} catch (java.lang.Exception ex_tFileInputDelimited_4) {
											globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
													ex_tFileInputDelimited_4.getMessage());
											rowstate_tFileInputDelimited_4.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Quantity", "row_Main",
															rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4],
															ex_tFileInputDelimited_4),
													ex_tFileInputDelimited_4));
										}
									} else {

										row_Main.Quantity = null;

									}

								} else {

									row_Main.Quantity = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 20;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									if (rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4].length() > 0) {
										try {

											row_Main.Discount = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4]);

										} catch (java.lang.Exception ex_tFileInputDelimited_4) {
											globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
													ex_tFileInputDelimited_4.getMessage());
											rowstate_tFileInputDelimited_4.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Discount", "row_Main",
															rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4],
															ex_tFileInputDelimited_4),
													ex_tFileInputDelimited_4));
										}
									} else {

										row_Main.Discount = null;

									}

								} else {

									row_Main.Discount = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 21;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									if (rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4].length() > 0) {
										try {

											row_Main.Profit = ParserUtils.parseTo_Double(
													rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4]);

										} catch (java.lang.Exception ex_tFileInputDelimited_4) {
											globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
													ex_tFileInputDelimited_4.getMessage());
											rowstate_tFileInputDelimited_4.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Profit", "row_Main",
															rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4],
															ex_tFileInputDelimited_4),
													ex_tFileInputDelimited_4));
										}
									} else {

										row_Main.Profit = null;

									}

								} else {

									row_Main.Profit = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 22;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									if (rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4].length() > 0) {
										try {

											row_Main.Shipping_Cost = ParserUtils.parseTo_Float(
													rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4]);

										} catch (java.lang.Exception ex_tFileInputDelimited_4) {
											globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
													ex_tFileInputDelimited_4.getMessage());
											rowstate_tFileInputDelimited_4.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Shipping_Cost", "row_Main",
															rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4],
															ex_tFileInputDelimited_4),
													ex_tFileInputDelimited_4));
										}
									} else {

										row_Main.Shipping_Cost = null;

									}

								} else {

									row_Main.Shipping_Cost = null;

								}

								columnIndexWithD_tFileInputDelimited_4 = 23;

								if (columnIndexWithD_tFileInputDelimited_4 < rowtFileInputDelimited_4.length) {

									row_Main.Order_Priority = rowtFileInputDelimited_4[columnIndexWithD_tFileInputDelimited_4];

								} else {

									row_Main.Order_Priority = null;

								}

							}

							if (rowstate_tFileInputDelimited_4.getException() != null) {
								throw rowstate_tFileInputDelimited_4.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_4 = true;

							System.err.println(e.getMessage());
							row_Main = null;

							globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE", e.getMessage());

						}

						/**
						 * [tFileInputDelimited_4 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_4 main ] start
						 */

						currentComponent = "tFileInputDelimited_4";

						tos_count_tFileInputDelimited_4++;

						/**
						 * [tFileInputDelimited_4 main ] stop
						 */

						/**
						 * [tFileInputDelimited_4 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_4";

						/**
						 * [tFileInputDelimited_4 process_data_begin ] stop
						 */
// Start of branch "row_Main"
						if (row_Main != null) {

							/**
							 * [tMap_5 main ] start
							 */

							currentComponent = "tMap_5";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row_Main"

								);
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_5 = false;

							// ###############################
							// # Input tables (lookups)
							boolean rejectedInnerJoin_tMap_5 = false;
							boolean mainRowRejected_tMap_5 = false;

							///////////////////////////////////////////////
							// Starting Lookup Table "row_XML"
							///////////////////////////////////////////////

							boolean forceLooprow_XML = false;

							row_XMLStruct row_XMLObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_5) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_5 = false;

								row_XMLHashKey.OrderID = row_Main.Order_ID;

								row_XMLHashKey.hashCodeDirty = true;

								tHash_Lookup_row_XML.lookup(row_XMLHashKey);

							} // G_TM_M_020

							if (tHash_Lookup_row_XML != null && tHash_Lookup_row_XML.getCount(row_XMLHashKey) > 1) { // G
																														// 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup
								// 'row_XML' and it contains more one result from keys : row_XML.OrderID = '" +
								// row_XMLHashKey.OrderID + "'");
							} // G 071

							row_XMLStruct row_XML = null;

							row_XMLStruct fromLookup_row_XML = null;
							row_XML = row_XMLDefault;

							if (tHash_Lookup_row_XML != null && tHash_Lookup_row_XML.hasNext()) { // G 099

								fromLookup_row_XML = tHash_Lookup_row_XML.next();

							} // G 099

							if (fromLookup_row_XML != null) {
								row_XML = fromLookup_row_XML;
							}

							///////////////////////////////////////////////
							// Starting Lookup Table "row_Loc"
							///////////////////////////////////////////////

							boolean forceLooprow_Loc = false;

							row_LocStruct row_LocObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_5) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_5 = false;

								row_LocHashKey.city = row_Main.City;

								row_LocHashKey.state = row_Main.State;

								row_LocHashKey.country = row_Main.Country;

								row_LocHashKey.hashCodeDirty = true;

								tHash_Lookup_row_Loc.lookup(row_LocHashKey);

							} // G_TM_M_020

							if (tHash_Lookup_row_Loc != null && tHash_Lookup_row_Loc.getCount(row_LocHashKey) > 1) { // G
																														// 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup
								// 'row_Loc' and it contains more one result from keys : row_Loc.city = '" +
								// row_LocHashKey.city + "', row_Loc.state = '" + row_LocHashKey.state + "',
								// row_Loc.country = '" + row_LocHashKey.country + "'");
							} // G 071

							row_LocStruct row_Loc = null;

							row_LocStruct fromLookup_row_Loc = null;
							row_Loc = row_LocDefault;

							if (tHash_Lookup_row_Loc != null && tHash_Lookup_row_Loc.hasNext()) { // G 099

								fromLookup_row_Loc = tHash_Lookup_row_Loc.next();

							} // G 099

							if (fromLookup_row_Loc != null) {
								row_Loc = fromLookup_row_Loc;
							}

							///////////////////////////////////////////////
							// Starting Lookup Table "row_Prod"
							///////////////////////////////////////////////

							boolean forceLooprow_Prod = false;

							row_ProdStruct row_ProdObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_5) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_5 = false;

								row_ProdHashKey.product_id_source = row_Main.Product_ID;

								row_ProdHashKey.hashCodeDirty = true;

								tHash_Lookup_row_Prod.lookup(row_ProdHashKey);

							} // G_TM_M_020

							if (tHash_Lookup_row_Prod != null && tHash_Lookup_row_Prod.getCount(row_ProdHashKey) > 1) { // G
																														// 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup
								// 'row_Prod' and it contains more one result from keys :
								// row_Prod.product_id_source = '" + row_ProdHashKey.product_id_source + "'");
							} // G 071

							row_ProdStruct row_Prod = null;

							row_ProdStruct fromLookup_row_Prod = null;
							row_Prod = row_ProdDefault;

							if (tHash_Lookup_row_Prod != null && tHash_Lookup_row_Prod.hasNext()) { // G 099

								fromLookup_row_Prod = tHash_Lookup_row_Prod.next();

							} // G 099

							if (fromLookup_row_Prod != null) {
								row_Prod = fromLookup_row_Prod;
							}

							///////////////////////////////////////////////
							// Starting Lookup Table "row_Cust"
							///////////////////////////////////////////////

							boolean forceLooprow_Cust = false;

							row_CustStruct row_CustObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_5) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_5 = false;

								row_CustHashKey.customer_id_source = row_Main.Customer_ID;

								row_CustHashKey.hashCodeDirty = true;

								tHash_Lookup_row_Cust.lookup(row_CustHashKey);

							} // G_TM_M_020

							if (tHash_Lookup_row_Cust != null && tHash_Lookup_row_Cust.getCount(row_CustHashKey) > 1) { // G
																														// 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup
								// 'row_Cust' and it contains more one result from keys :
								// row_Cust.customer_id_source = '" + row_CustHashKey.customer_id_source + "'");
							} // G 071

							row_CustStruct row_Cust = null;

							row_CustStruct fromLookup_row_Cust = null;
							row_Cust = row_CustDefault;

							if (tHash_Lookup_row_Cust != null && tHash_Lookup_row_Cust.hasNext()) { // G 099

								fromLookup_row_Cust = tHash_Lookup_row_Cust.next();

							} // G 099

							if (fromLookup_row_Cust != null) {
								row_Cust = fromLookup_row_Cust;
							}

							///////////////////////////////////////////////
							// Starting Lookup Table "row_Mgr"
							///////////////////////////////////////////////

							boolean forceLooprow_Mgr = false;

							row_MgrStruct row_MgrObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_5) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_5 = false;

								row_MgrHashKey.region = row_Main.Region;

								row_MgrHashKey.hashCodeDirty = true;

								tHash_Lookup_row_Mgr.lookup(row_MgrHashKey);

							} // G_TM_M_020

							if (tHash_Lookup_row_Mgr != null && tHash_Lookup_row_Mgr.getCount(row_MgrHashKey) > 1) { // G
																														// 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup
								// 'row_Mgr' and it contains more one result from keys : row_Mgr.region = '" +
								// row_MgrHashKey.region + "'");
							} // G 071

							row_MgrStruct row_Mgr = null;

							row_MgrStruct fromLookup_row_Mgr = null;
							row_Mgr = row_MgrDefault;

							if (tHash_Lookup_row_Mgr != null && tHash_Lookup_row_Mgr.hasNext()) { // G 099

								fromLookup_row_Mgr = tHash_Lookup_row_Mgr.next();

							} // G 099

							if (fromLookup_row_Mgr != null) {
								row_Mgr = fromLookup_row_Mgr;
							}

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_5__Struct Var = Var__tMap_5;// ###############################
								// ###############################
								// # Output tables

								Out_LocationF = null;

// # Output table : 'Out_LocationF'
								Out_LocationF_tmp.fact_id = 0;
								Out_LocationF_tmp.location_key = row_Loc.location_key;
								Out_LocationF_tmp.product_key = row_Prod.product_key;
								Out_LocationF_tmp.customer_key = row_Cust.customer_key;
								Out_LocationF_tmp.manager_key = row_Mgr.manager_key;
								Out_LocationF_tmp.order_id = row_Main.Order_ID;
								Out_LocationF_tmp.order_date = row_Main.Order_Date;
								Out_LocationF_tmp.ship_date = row_Main.Ship_Date;
								Out_LocationF_tmp.ship_mode = row_Main.Ship_Mode;
								Out_LocationF_tmp.sales = row_Main.Sales;
								Out_LocationF_tmp.quantity = row_Main.Quantity;
								Out_LocationF_tmp.discount = row_Main.Discount;
								Out_LocationF_tmp.profit = row_Main.Profit;
								Out_LocationF_tmp.shipping_cost = row_Main.Shipping_Cost;
								Out_LocationF_tmp.is_returned = row_XML.OrderID != null;
								Out_LocationF_tmp.return_reason = row_XML.Reason;
								Out_LocationF = Out_LocationF_tmp;
// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_5 = false;

							tos_count_tMap_5++;

							/**
							 * [tMap_5 main ] stop
							 */

							/**
							 * [tMap_5 process_data_begin ] start
							 */

							currentComponent = "tMap_5";

							/**
							 * [tMap_5 process_data_begin ] stop
							 */
// Start of branch "Out_LocationF"
							if (Out_LocationF != null) {

								/**
								 * [tDBOutput_5 main ] start
								 */

								currentComponent = "tDBOutput_5";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "Out_LocationF"

									);
								}

								whetherReject_tDBOutput_5 = false;
								if (Out_LocationF.location_key == null) {
									pstmt_tDBOutput_5.setNull(1, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_5.setInt(1, Out_LocationF.location_key);
								}

								if (Out_LocationF.product_key == null) {
									pstmt_tDBOutput_5.setNull(2, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_5.setInt(2, Out_LocationF.product_key);
								}

								if (Out_LocationF.customer_key == null) {
									pstmt_tDBOutput_5.setNull(3, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_5.setInt(3, Out_LocationF.customer_key);
								}

								if (Out_LocationF.manager_key == null) {
									pstmt_tDBOutput_5.setNull(4, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_5.setInt(4, Out_LocationF.manager_key);
								}

								if (Out_LocationF.order_id == null) {
									pstmt_tDBOutput_5.setNull(5, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_5.setString(5, Out_LocationF.order_id);
								}

								if (Out_LocationF.order_date != null) {
									pstmt_tDBOutput_5.setTimestamp(6,
											new java.sql.Timestamp(Out_LocationF.order_date.getTime()));
								} else {
									pstmt_tDBOutput_5.setNull(6, java.sql.Types.TIMESTAMP);
								}

								if (Out_LocationF.ship_date != null) {
									pstmt_tDBOutput_5.setTimestamp(7,
											new java.sql.Timestamp(Out_LocationF.ship_date.getTime()));
								} else {
									pstmt_tDBOutput_5.setNull(7, java.sql.Types.TIMESTAMP);
								}

								if (Out_LocationF.ship_mode == null) {
									pstmt_tDBOutput_5.setNull(8, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_5.setString(8, Out_LocationF.ship_mode);
								}

								if (Out_LocationF.sales == null) {
									pstmt_tDBOutput_5.setNull(9, java.sql.Types.FLOAT);
								} else {
									pstmt_tDBOutput_5.setFloat(9, Out_LocationF.sales);
								}

								if (Out_LocationF.quantity == null) {
									pstmt_tDBOutput_5.setNull(10, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_5.setInt(10, Out_LocationF.quantity);
								}

								if (Out_LocationF.discount == null) {
									pstmt_tDBOutput_5.setNull(11, java.sql.Types.FLOAT);
								} else {
									pstmt_tDBOutput_5.setFloat(11, Out_LocationF.discount);
								}

								if (Out_LocationF.profit == null) {
									pstmt_tDBOutput_5.setNull(12, java.sql.Types.DOUBLE);
								} else {
									pstmt_tDBOutput_5.setDouble(12, Out_LocationF.profit);
								}

								if (Out_LocationF.shipping_cost == null) {
									pstmt_tDBOutput_5.setNull(13, java.sql.Types.FLOAT);
								} else {
									pstmt_tDBOutput_5.setFloat(13, Out_LocationF.shipping_cost);
								}

								if (Out_LocationF.is_returned == null) {
									pstmt_tDBOutput_5.setNull(14, java.sql.Types.BOOLEAN);
								} else {
									pstmt_tDBOutput_5.setBoolean(14, Out_LocationF.is_returned);
								}

								if (Out_LocationF.return_reason == null) {
									pstmt_tDBOutput_5.setNull(15, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_5.setString(15, Out_LocationF.return_reason);
								}

								pstmt_tDBOutput_5.addBatch();
								nb_line_tDBOutput_5++;

								batchSizeCounter_tDBOutput_5++;

								if ((batchSize_tDBOutput_5 > 0)
										&& (batchSize_tDBOutput_5 <= batchSizeCounter_tDBOutput_5)) {
									try {
										int countSum_tDBOutput_5 = 0;

										for (int countEach_tDBOutput_5 : pstmt_tDBOutput_5.executeBatch()) {
											countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0
													: countEach_tDBOutput_5);
										}
										rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;

										insertedCount_tDBOutput_5 += countSum_tDBOutput_5;

										batchSizeCounter_tDBOutput_5 = 0;
									} catch (java.sql.BatchUpdateException e_tDBOutput_5) {
										globalMap.put("tDBOutput_5_ERROR_MESSAGE", e_tDBOutput_5.getMessage());
										java.sql.SQLException ne_tDBOutput_5 = e_tDBOutput_5.getNextException(),
												sqle_tDBOutput_5 = null;
										String errormessage_tDBOutput_5;
										if (ne_tDBOutput_5 != null) {
											// build new exception to provide the original cause
											sqle_tDBOutput_5 = new java.sql.SQLException(
													e_tDBOutput_5.getMessage() + "\ncaused by: "
															+ ne_tDBOutput_5.getMessage(),
													ne_tDBOutput_5.getSQLState(), ne_tDBOutput_5.getErrorCode(),
													ne_tDBOutput_5);
											errormessage_tDBOutput_5 = sqle_tDBOutput_5.getMessage();
										} else {
											errormessage_tDBOutput_5 = e_tDBOutput_5.getMessage();
										}

										int countSum_tDBOutput_5 = 0;
										for (int countEach_tDBOutput_5 : e_tDBOutput_5.getUpdateCounts()) {
											countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0
													: countEach_tDBOutput_5);
										}
										rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;

										insertedCount_tDBOutput_5 += countSum_tDBOutput_5;

										System.err.println(errormessage_tDBOutput_5);

									}
								}

								commitCounter_tDBOutput_5++;
								if (commitEvery_tDBOutput_5 <= commitCounter_tDBOutput_5) {
									if ((batchSize_tDBOutput_5 > 0) && (batchSizeCounter_tDBOutput_5 > 0)) {
										try {
											int countSum_tDBOutput_5 = 0;

											for (int countEach_tDBOutput_5 : pstmt_tDBOutput_5.executeBatch()) {
												countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0
														: countEach_tDBOutput_5);
											}
											rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;

											insertedCount_tDBOutput_5 += countSum_tDBOutput_5;

											batchSizeCounter_tDBOutput_5 = 0;
										} catch (java.sql.BatchUpdateException e_tDBOutput_5) {
											globalMap.put("tDBOutput_5_ERROR_MESSAGE", e_tDBOutput_5.getMessage());
											java.sql.SQLException ne_tDBOutput_5 = e_tDBOutput_5.getNextException(),
													sqle_tDBOutput_5 = null;
											String errormessage_tDBOutput_5;
											if (ne_tDBOutput_5 != null) {
												// build new exception to provide the original cause
												sqle_tDBOutput_5 = new java.sql.SQLException(
														e_tDBOutput_5.getMessage() + "\ncaused by: "
																+ ne_tDBOutput_5.getMessage(),
														ne_tDBOutput_5.getSQLState(), ne_tDBOutput_5.getErrorCode(),
														ne_tDBOutput_5);
												errormessage_tDBOutput_5 = sqle_tDBOutput_5.getMessage();
											} else {
												errormessage_tDBOutput_5 = e_tDBOutput_5.getMessage();
											}

											int countSum_tDBOutput_5 = 0;
											for (int countEach_tDBOutput_5 : e_tDBOutput_5.getUpdateCounts()) {
												countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0
														: countEach_tDBOutput_5);
											}
											rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;

											insertedCount_tDBOutput_5 += countSum_tDBOutput_5;

											System.err.println(errormessage_tDBOutput_5);

										}
									}
									if (rowsToCommitCount_tDBOutput_5 != 0) {

									}
									conn_tDBOutput_5.commit();
									if (rowsToCommitCount_tDBOutput_5 != 0) {

										rowsToCommitCount_tDBOutput_5 = 0;
									}
									commitCounter_tDBOutput_5 = 0;
								}

								tos_count_tDBOutput_5++;

								/**
								 * [tDBOutput_5 main ] stop
								 */

								/**
								 * [tDBOutput_5 process_data_begin ] start
								 */

								currentComponent = "tDBOutput_5";

								/**
								 * [tDBOutput_5 process_data_begin ] stop
								 */

								/**
								 * [tDBOutput_5 process_data_end ] start
								 */

								currentComponent = "tDBOutput_5";

								/**
								 * [tDBOutput_5 process_data_end ] stop
								 */

							} // End of branch "Out_LocationF"

							/**
							 * [tMap_5 process_data_end ] start
							 */

							currentComponent = "tMap_5";

							/**
							 * [tMap_5 process_data_end ] stop
							 */

						} // End of branch "row_Main"

						/**
						 * [tFileInputDelimited_4 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_4";

						/**
						 * [tFileInputDelimited_4 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_4 end ] start
						 */

						currentComponent = "tFileInputDelimited_4";

						nb_line_tFileInputDelimited_4++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_4 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_4 != null) {
							csvReadertFileInputDelimited_4.close();
						}
					}
					if (csvReadertFileInputDelimited_4 != null) {
						globalMap.put("tFileInputDelimited_4_NB_LINE", nb_line_tFileInputDelimited_4);
					}

				}

				ok_Hash.put("tFileInputDelimited_4", true);
				end_Hash.put("tFileInputDelimited_4", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_4 end ] stop
				 */

				/**
				 * [tMap_5 end ] start
				 */

				currentComponent = "tMap_5";

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row_XML != null) {
					tHash_Lookup_row_XML.endGet();
				}
				globalMap.remove("tHash_Lookup_row_XML");

				if (tHash_Lookup_row_Loc != null) {
					tHash_Lookup_row_Loc.endGet();
				}
				globalMap.remove("tHash_Lookup_row_Loc");

				if (tHash_Lookup_row_Prod != null) {
					tHash_Lookup_row_Prod.endGet();
				}
				globalMap.remove("tHash_Lookup_row_Prod");

				if (tHash_Lookup_row_Cust != null) {
					tHash_Lookup_row_Cust.endGet();
				}
				globalMap.remove("tHash_Lookup_row_Cust");

				if (tHash_Lookup_row_Mgr != null) {
					tHash_Lookup_row_Mgr.endGet();
				}
				globalMap.remove("tHash_Lookup_row_Mgr");

// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row_Main");
				}

				ok_Hash.put("tMap_5", true);
				end_Hash.put("tMap_5", System.currentTimeMillis());

				/**
				 * [tMap_5 end ] stop
				 */

				/**
				 * [tDBOutput_5 end ] start
				 */

				currentComponent = "tDBOutput_5";

				try {
					int countSum_tDBOutput_5 = 0;
					if (pstmt_tDBOutput_5 != null && batchSizeCounter_tDBOutput_5 > 0) {

						for (int countEach_tDBOutput_5 : pstmt_tDBOutput_5.executeBatch()) {
							countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0 : countEach_tDBOutput_5);
						}
						rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;

					}

					insertedCount_tDBOutput_5 += countSum_tDBOutput_5;

				} catch (java.sql.BatchUpdateException e_tDBOutput_5) {
					globalMap.put("tDBOutput_5_ERROR_MESSAGE", e_tDBOutput_5.getMessage());
					java.sql.SQLException ne_tDBOutput_5 = e_tDBOutput_5.getNextException(), sqle_tDBOutput_5 = null;
					String errormessage_tDBOutput_5;
					if (ne_tDBOutput_5 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_5 = new java.sql.SQLException(
								e_tDBOutput_5.getMessage() + "\ncaused by: " + ne_tDBOutput_5.getMessage(),
								ne_tDBOutput_5.getSQLState(), ne_tDBOutput_5.getErrorCode(), ne_tDBOutput_5);
						errormessage_tDBOutput_5 = sqle_tDBOutput_5.getMessage();
					} else {
						errormessage_tDBOutput_5 = e_tDBOutput_5.getMessage();
					}

					int countSum_tDBOutput_5 = 0;
					for (int countEach_tDBOutput_5 : e_tDBOutput_5.getUpdateCounts()) {
						countSum_tDBOutput_5 += (countEach_tDBOutput_5 < 0 ? 0 : countEach_tDBOutput_5);
					}
					rowsToCommitCount_tDBOutput_5 += countSum_tDBOutput_5;

					insertedCount_tDBOutput_5 += countSum_tDBOutput_5;

					System.err.println(errormessage_tDBOutput_5);

				}

				if (pstmt_tDBOutput_5 != null) {

					pstmt_tDBOutput_5.close();
					resourceMap.remove("pstmt_tDBOutput_5");
				}
				resourceMap.put("statementClosed_tDBOutput_5", true);
				if (rowsToCommitCount_tDBOutput_5 != 0) {

				}
				conn_tDBOutput_5.commit();
				if (rowsToCommitCount_tDBOutput_5 != 0) {

					rowsToCommitCount_tDBOutput_5 = 0;
				}
				commitCounter_tDBOutput_5 = 0;

				conn_tDBOutput_5.close();

				resourceMap.put("finish_tDBOutput_5", true);

				nb_line_deleted_tDBOutput_5 = nb_line_deleted_tDBOutput_5 + deletedCount_tDBOutput_5;
				nb_line_update_tDBOutput_5 = nb_line_update_tDBOutput_5 + updatedCount_tDBOutput_5;
				nb_line_inserted_tDBOutput_5 = nb_line_inserted_tDBOutput_5 + insertedCount_tDBOutput_5;
				nb_line_rejected_tDBOutput_5 = nb_line_rejected_tDBOutput_5 + rejectedCount_tDBOutput_5;

				globalMap.put("tDBOutput_5_NB_LINE", nb_line_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_UPDATED", nb_line_update_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_DELETED", nb_line_deleted_tDBOutput_5);
				globalMap.put("tDBOutput_5_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_5);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "Out_LocationF");
				}

				ok_Hash.put("tDBOutput_5", true);
				end_Hash.put("tDBOutput_5", System.currentTimeMillis());

				/**
				 * [tDBOutput_5 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			// free memory for "tMap_5"
			globalMap.remove("tHash_Lookup_row_Cust");

			// free memory for "tMap_5"
			globalMap.remove("tHash_Lookup_row_Loc");

			// free memory for "tMap_5"
			globalMap.remove("tHash_Lookup_row_Mgr");

			// free memory for "tMap_5"
			globalMap.remove("tHash_Lookup_row_Prod");

			// free memory for "tMap_5"
			globalMap.remove("tHash_Lookup_row_XML");

			try {

				/**
				 * [tFileInputDelimited_4 finally ] start
				 */

				currentComponent = "tFileInputDelimited_4";

				/**
				 * [tFileInputDelimited_4 finally ] stop
				 */

				/**
				 * [tMap_5 finally ] start
				 */

				currentComponent = "tMap_5";

				/**
				 * [tMap_5 finally ] stop
				 */

				/**
				 * [tDBOutput_5 finally ] start
				 */

				currentComponent = "tDBOutput_5";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_5") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_5 = null;
						if ((pstmtToClose_tDBOutput_5 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_5")) != null) {
							pstmtToClose_tDBOutput_5.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_5") == null) {
						java.sql.Connection ctn_tDBOutput_5 = null;
						if ((ctn_tDBOutput_5 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_5")) != null) {
							try {
								ctn_tDBOutput_5.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_5) {
								String errorMessage_tDBOutput_5 = "failed to close the connection in tDBOutput_5 :"
										+ sqlEx_tDBOutput_5.getMessage();
								System.err.println(errorMessage_tDBOutput_5);
							}
						}
					}
				}

				/**
				 * [tDBOutput_5 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_4_SUBPROCESS_STATE", 1);
	}

	public static class row_CustStruct implements routines.system.IPersistableComparableLookupRow<row_CustStruct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int customer_key;

		public int getCustomer_key() {
			return this.customer_key;
		}

		public String customer_id_source;

		public String getCustomer_id_source() {
			return this.customer_id_source;
		}

		public String customer_name;

		public String getCustomer_name() {
			return this.customer_name;
		}

		public String segment;

		public String getSegment() {
			return this.segment;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.customer_id_source == null) ? 0 : this.customer_id_source.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row_CustStruct other = (row_CustStruct) obj;

			if (this.customer_id_source == null) {
				if (other.customer_id_source != null)
					return false;

			} else if (!this.customer_id_source.equals(other.customer_id_source))

				return false;

			return true;
		}

		public void copyDataTo(row_CustStruct other) {

			other.customer_key = this.customer_key;
			other.customer_id_source = this.customer_id_source;
			other.customer_name = this.customer_name;
			other.segment = this.segment;

		}

		public void copyKeysDataTo(row_CustStruct other) {

			other.customer_id_source = this.customer_id_source;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.customer_id_source = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.customer_id_source = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.customer_id_source, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.customer_id_source, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.customer_key = dis.readInt();

				this.customer_name = readString(dis, ois);

				this.segment = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.customer_key = objectIn.readInt();

				this.customer_name = readString(dis, objectIn);

				this.segment = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.customer_key);

				writeString(this.customer_name, dos, oos);

				writeString(this.segment, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.customer_key);

				writeString(this.customer_name, dos, objectOut);

				writeString(this.segment, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("customer_key=" + String.valueOf(customer_key));
			sb.append(",customer_id_source=" + customer_id_source);
			sb.append(",customer_name=" + customer_name);
			sb.append(",segment=" + segment);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row_CustStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.customer_id_source, other.customer_id_source);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row_CustStruct row_Cust = new row_CustStruct();

				/**
				 * [tAdvancedHash_row_Cust begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row_Cust", false);
				start_Hash.put("tAdvancedHash_row_Cust", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row_Cust";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row_Cust");
				}

				int tos_count_tAdvancedHash_row_Cust = 0;

				// connection name:row_Cust
				// source node:tDBInput_1 - inputs:(after_tFileInputDelimited_4)
				// outputs:(row_Cust,row_Cust) | target node:tAdvancedHash_row_Cust -
				// inputs:(row_Cust) outputs:()
				// linked node: tMap_5 -
				// inputs:(row_Main,row_Cust,row_Loc,row_Mgr,row_Prod,row_XML)
				// outputs:(Out_LocationF)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row_Cust = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_CustStruct> tHash_Lookup_row_Cust = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row_CustStruct>getLookup(matchingModeEnum_row_Cust);

				globalMap.put("tHash_Lookup_row_Cust", tHash_Lookup_row_Cust);

				/**
				 * [tAdvancedHash_row_Cust begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				ok_Hash.put("tDBInput_1", false);
				start_Hash.put("tDBInput_1", System.currentTimeMillis());

				currentComponent = "tDBInput_1";

				int tos_count_tDBInput_1 = 0;

				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				String driverClass_tDBInput_1 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_1 = java.lang.Class.forName(driverClass_tDBInput_1);
				String dbUser_tDBInput_1 = "postgres";

				final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:Hsx1HSzjBbTn8TBwuALNvXFV22qfR+5HI4KzVs+i5w1MZGVpGA==");

				String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

				String url_tDBInput_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "GlobalRetailDW";

				conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
						dbPwd_tDBInput_1);

				conn_tDBInput_1.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "SELECT \n  \"public\".\"dim_customer\".\"customer_key\", \n  \"public\".\"dim_customer\".\"customer_id_source\", \n  \"pub"
						+ "lic\".\"dim_customer\".\"customer_name\", \n  \"public\".\"dim_customer\".\"segment\"\nFROM \"public\".\"dim_customer\"";

				globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);
				java.sql.ResultSet rs_tDBInput_1 = null;

				try {
					rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
					int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

					String tmpContent_tDBInput_1 = null;

					while (rs_tDBInput_1.next()) {
						nb_line_tDBInput_1++;

						if (colQtyInRs_tDBInput_1 < 1) {
							row_Cust.customer_key = 0;
						} else {

							row_Cust.customer_key = rs_tDBInput_1.getInt(1);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row_Cust.customer_id_source = null;
						} else {

							row_Cust.customer_id_source = routines.system.JDBCUtil.getString(rs_tDBInput_1, 2, false);
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row_Cust.customer_name = null;
						} else {

							row_Cust.customer_name = routines.system.JDBCUtil.getString(rs_tDBInput_1, 3, false);
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row_Cust.segment = null;
						} else {

							row_Cust.segment = routines.system.JDBCUtil.getString(rs_tDBInput_1, 4, false);
						}

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						currentComponent = "tDBInput_1";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row_Cust main ] start
						 */

						currentComponent = "tAdvancedHash_row_Cust";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row_Cust"

							);
						}

						row_CustStruct row_Cust_HashRow = new row_CustStruct();

						row_Cust_HashRow.customer_key = row_Cust.customer_key;

						row_Cust_HashRow.customer_id_source = row_Cust.customer_id_source;

						row_Cust_HashRow.customer_name = row_Cust.customer_name;

						row_Cust_HashRow.segment = row_Cust.segment;

						tHash_Lookup_row_Cust.put(row_Cust_HashRow);

						tos_count_tAdvancedHash_row_Cust++;

						/**
						 * [tAdvancedHash_row_Cust main ] stop
						 */

						/**
						 * [tAdvancedHash_row_Cust process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row_Cust";

						/**
						 * [tAdvancedHash_row_Cust process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row_Cust process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row_Cust";

						/**
						 * [tAdvancedHash_row_Cust process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 process_data_end ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						currentComponent = "tDBInput_1";

					}
				} finally {
					if (rs_tDBInput_1 != null) {
						rs_tDBInput_1.close();
					}
					if (stmt_tDBInput_1 != null) {
						stmt_tDBInput_1.close();
					}
					if (conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {

						conn_tDBInput_1.commit();

						conn_tDBInput_1.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}
				globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);

				ok_Hash.put("tDBInput_1", true);
				end_Hash.put("tDBInput_1", System.currentTimeMillis());

				/**
				 * [tDBInput_1 end ] stop
				 */

				/**
				 * [tAdvancedHash_row_Cust end ] start
				 */

				currentComponent = "tAdvancedHash_row_Cust";

				tHash_Lookup_row_Cust.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row_Cust");
				}

				ok_Hash.put("tAdvancedHash_row_Cust", true);
				end_Hash.put("tAdvancedHash_row_Cust", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row_Cust end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				currentComponent = "tDBInput_1";

				/**
				 * [tDBInput_1 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row_Cust finally ] start
				 */

				currentComponent = "tAdvancedHash_row_Cust";

				/**
				 * [tAdvancedHash_row_Cust finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public static class row_LocStruct implements routines.system.IPersistableComparableLookupRow<row_LocStruct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int location_key;

		public int getLocation_key() {
			return this.location_key;
		}

		public String city;

		public String getCity() {
			return this.city;
		}

		public String state;

		public String getState() {
			return this.state;
		}

		public String country;

		public String getCountry() {
			return this.country;
		}

		public String region;

		public String getRegion() {
			return this.region;
		}

		public String market;

		public String getMarket() {
			return this.market;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.city == null) ? 0 : this.city.hashCode());

				result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());

				result = prime * result + ((this.country == null) ? 0 : this.country.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row_LocStruct other = (row_LocStruct) obj;

			if (this.city == null) {
				if (other.city != null)
					return false;

			} else if (!this.city.equals(other.city))

				return false;

			if (this.state == null) {
				if (other.state != null)
					return false;

			} else if (!this.state.equals(other.state))

				return false;

			if (this.country == null) {
				if (other.country != null)
					return false;

			} else if (!this.country.equals(other.country))

				return false;

			return true;
		}

		public void copyDataTo(row_LocStruct other) {

			other.location_key = this.location_key;
			other.city = this.city;
			other.state = this.state;
			other.country = this.country;
			other.region = this.region;
			other.market = this.market;

		}

		public void copyKeysDataTo(row_LocStruct other) {

			other.city = this.city;
			other.state = this.state;
			other.country = this.country;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.city = readString(dis);

					this.state = readString(dis);

					this.country = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.city = readString(dis);

					this.state = readString(dis);

					this.country = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.city, dos);

				// String

				writeString(this.state, dos);

				// String

				writeString(this.country, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.city, dos);

				// String

				writeString(this.state, dos);

				// String

				writeString(this.country, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.location_key = dis.readInt();

				this.region = readString(dis, ois);

				this.market = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.location_key = objectIn.readInt();

				this.region = readString(dis, objectIn);

				this.market = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.location_key);

				writeString(this.region, dos, oos);

				writeString(this.market, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.location_key);

				writeString(this.region, dos, objectOut);

				writeString(this.market, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("location_key=" + String.valueOf(location_key));
			sb.append(",city=" + city);
			sb.append(",state=" + state);
			sb.append(",country=" + country);
			sb.append(",region=" + region);
			sb.append(",market=" + market);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row_LocStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.city, other.city);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.state, other.state);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.country, other.country);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row_LocStruct row_Loc = new row_LocStruct();

				/**
				 * [tAdvancedHash_row_Loc begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row_Loc", false);
				start_Hash.put("tAdvancedHash_row_Loc", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row_Loc";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row_Loc");
				}

				int tos_count_tAdvancedHash_row_Loc = 0;

				// connection name:row_Loc
				// source node:tDBInput_2 - inputs:(after_tFileInputDelimited_4)
				// outputs:(row_Loc,row_Loc) | target node:tAdvancedHash_row_Loc -
				// inputs:(row_Loc) outputs:()
				// linked node: tMap_5 -
				// inputs:(row_Main,row_Cust,row_Loc,row_Mgr,row_Prod,row_XML)
				// outputs:(Out_LocationF)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row_Loc = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_LocStruct> tHash_Lookup_row_Loc = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row_LocStruct>getLookup(matchingModeEnum_row_Loc);

				globalMap.put("tHash_Lookup_row_Loc", tHash_Lookup_row_Loc);

				/**
				 * [tAdvancedHash_row_Loc begin ] stop
				 */

				/**
				 * [tDBInput_2 begin ] start
				 */

				ok_Hash.put("tDBInput_2", false);
				start_Hash.put("tDBInput_2", System.currentTimeMillis());

				currentComponent = "tDBInput_2";

				int tos_count_tDBInput_2 = 0;

				int nb_line_tDBInput_2 = 0;
				java.sql.Connection conn_tDBInput_2 = null;
				String driverClass_tDBInput_2 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_2 = java.lang.Class.forName(driverClass_tDBInput_2);
				String dbUser_tDBInput_2 = "postgres";

				final String decryptedPassword_tDBInput_2 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:OQ+8yfkINTxLTFx1BwP2inQT8l9mNXvdX8wYRYDL7/KqsR/Qhw==");

				String dbPwd_tDBInput_2 = decryptedPassword_tDBInput_2;

				String url_tDBInput_2 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "GlobalRetailDW";

				conn_tDBInput_2 = java.sql.DriverManager.getConnection(url_tDBInput_2, dbUser_tDBInput_2,
						dbPwd_tDBInput_2);

				conn_tDBInput_2.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();

				String dbquery_tDBInput_2 = "SELECT \n  \"public\".\"dim_location\".\"location_key\", \n  \"public\".\"dim_location\".\"city\", \n  \"public\".\"dim_lo"
						+ "cation\".\"state\", \n  \"public\".\"dim_location\".\"country\", \n  \"public\".\"dim_location\".\"region\", \n  \"public\""
						+ ".\"dim_location\".\"market\"\nFROM \"public\".\"dim_location\"";

				globalMap.put("tDBInput_2_QUERY", dbquery_tDBInput_2);
				java.sql.ResultSet rs_tDBInput_2 = null;

				try {
					rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
					java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
					int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();

					String tmpContent_tDBInput_2 = null;

					while (rs_tDBInput_2.next()) {
						nb_line_tDBInput_2++;

						if (colQtyInRs_tDBInput_2 < 1) {
							row_Loc.location_key = 0;
						} else {

							row_Loc.location_key = rs_tDBInput_2.getInt(1);
							if (rs_tDBInput_2.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_2 < 2) {
							row_Loc.city = null;
						} else {

							row_Loc.city = routines.system.JDBCUtil.getString(rs_tDBInput_2, 2, false);
						}
						if (colQtyInRs_tDBInput_2 < 3) {
							row_Loc.state = null;
						} else {

							row_Loc.state = routines.system.JDBCUtil.getString(rs_tDBInput_2, 3, false);
						}
						if (colQtyInRs_tDBInput_2 < 4) {
							row_Loc.country = null;
						} else {

							row_Loc.country = routines.system.JDBCUtil.getString(rs_tDBInput_2, 4, false);
						}
						if (colQtyInRs_tDBInput_2 < 5) {
							row_Loc.region = null;
						} else {

							row_Loc.region = routines.system.JDBCUtil.getString(rs_tDBInput_2, 5, false);
						}
						if (colQtyInRs_tDBInput_2 < 6) {
							row_Loc.market = null;
						} else {

							row_Loc.market = routines.system.JDBCUtil.getString(rs_tDBInput_2, 6, false);
						}

						/**
						 * [tDBInput_2 begin ] stop
						 */

						/**
						 * [tDBInput_2 main ] start
						 */

						currentComponent = "tDBInput_2";

						tos_count_tDBInput_2++;

						/**
						 * [tDBInput_2 main ] stop
						 */

						/**
						 * [tDBInput_2 process_data_begin ] start
						 */

						currentComponent = "tDBInput_2";

						/**
						 * [tDBInput_2 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row_Loc main ] start
						 */

						currentComponent = "tAdvancedHash_row_Loc";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row_Loc"

							);
						}

						row_LocStruct row_Loc_HashRow = new row_LocStruct();

						row_Loc_HashRow.location_key = row_Loc.location_key;

						row_Loc_HashRow.city = row_Loc.city;

						row_Loc_HashRow.state = row_Loc.state;

						row_Loc_HashRow.country = row_Loc.country;

						row_Loc_HashRow.region = row_Loc.region;

						row_Loc_HashRow.market = row_Loc.market;

						tHash_Lookup_row_Loc.put(row_Loc_HashRow);

						tos_count_tAdvancedHash_row_Loc++;

						/**
						 * [tAdvancedHash_row_Loc main ] stop
						 */

						/**
						 * [tAdvancedHash_row_Loc process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row_Loc";

						/**
						 * [tAdvancedHash_row_Loc process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row_Loc process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row_Loc";

						/**
						 * [tAdvancedHash_row_Loc process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 process_data_end ] start
						 */

						currentComponent = "tDBInput_2";

						/**
						 * [tDBInput_2 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 end ] start
						 */

						currentComponent = "tDBInput_2";

					}
				} finally {
					if (rs_tDBInput_2 != null) {
						rs_tDBInput_2.close();
					}
					if (stmt_tDBInput_2 != null) {
						stmt_tDBInput_2.close();
					}
					if (conn_tDBInput_2 != null && !conn_tDBInput_2.isClosed()) {

						conn_tDBInput_2.commit();

						conn_tDBInput_2.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}
				globalMap.put("tDBInput_2_NB_LINE", nb_line_tDBInput_2);

				ok_Hash.put("tDBInput_2", true);
				end_Hash.put("tDBInput_2", System.currentTimeMillis());

				/**
				 * [tDBInput_2 end ] stop
				 */

				/**
				 * [tAdvancedHash_row_Loc end ] start
				 */

				currentComponent = "tAdvancedHash_row_Loc";

				tHash_Lookup_row_Loc.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row_Loc");
				}

				ok_Hash.put("tAdvancedHash_row_Loc", true);
				end_Hash.put("tAdvancedHash_row_Loc", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row_Loc end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_2 finally ] start
				 */

				currentComponent = "tDBInput_2";

				/**
				 * [tDBInput_2 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row_Loc finally ] start
				 */

				currentComponent = "tAdvancedHash_row_Loc";

				/**
				 * [tAdvancedHash_row_Loc finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
	}

	public static class row_MgrStruct implements routines.system.IPersistableComparableLookupRow<row_MgrStruct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int manager_key;

		public int getManager_key() {
			return this.manager_key;
		}

		public String region;

		public String getRegion() {
			return this.region;
		}

		public String manager_name;

		public String getManager_name() {
			return this.manager_name;
		}

		public BigDecimal yearly_target;

		public BigDecimal getYearly_target() {
			return this.yearly_target;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.region == null) ? 0 : this.region.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row_MgrStruct other = (row_MgrStruct) obj;

			if (this.region == null) {
				if (other.region != null)
					return false;

			} else if (!this.region.equals(other.region))

				return false;

			return true;
		}

		public void copyDataTo(row_MgrStruct other) {

			other.manager_key = this.manager_key;
			other.region = this.region;
			other.manager_name = this.manager_name;
			other.yearly_target = this.yearly_target;

		}

		public void copyKeysDataTo(row_MgrStruct other) {

			other.region = this.region;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.region = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.region = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.region, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.region, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.manager_key = dis.readInt();

				this.manager_name = readString(dis, ois);

				this.yearly_target = (BigDecimal) ois.readObject();

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.manager_key = objectIn.readInt();

				this.manager_name = readString(dis, objectIn);

				this.yearly_target = (BigDecimal) objectIn.readObject();

			} catch (IOException e) {
				throw new RuntimeException(e);

			} catch (ClassNotFoundException eCNFE) {
				throw new RuntimeException(eCNFE);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.manager_key);

				writeString(this.manager_name, dos, oos);

				oos.writeObject(this.yearly_target);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.manager_key);

				writeString(this.manager_name, dos, objectOut);

				objectOut.writeObject(this.yearly_target);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("manager_key=" + String.valueOf(manager_key));
			sb.append(",region=" + region);
			sb.append(",manager_name=" + manager_name);
			sb.append(",yearly_target=" + String.valueOf(yearly_target));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row_MgrStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.region, other.region);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row_MgrStruct row_Mgr = new row_MgrStruct();

				/**
				 * [tAdvancedHash_row_Mgr begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row_Mgr", false);
				start_Hash.put("tAdvancedHash_row_Mgr", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row_Mgr";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row_Mgr");
				}

				int tos_count_tAdvancedHash_row_Mgr = 0;

				// connection name:row_Mgr
				// source node:tDBInput_3 - inputs:(after_tFileInputDelimited_4)
				// outputs:(row_Mgr,row_Mgr) | target node:tAdvancedHash_row_Mgr -
				// inputs:(row_Mgr) outputs:()
				// linked node: tMap_5 -
				// inputs:(row_Main,row_Cust,row_Loc,row_Mgr,row_Prod,row_XML)
				// outputs:(Out_LocationF)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row_Mgr = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_MgrStruct> tHash_Lookup_row_Mgr = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row_MgrStruct>getLookup(matchingModeEnum_row_Mgr);

				globalMap.put("tHash_Lookup_row_Mgr", tHash_Lookup_row_Mgr);

				/**
				 * [tAdvancedHash_row_Mgr begin ] stop
				 */

				/**
				 * [tDBInput_3 begin ] start
				 */

				ok_Hash.put("tDBInput_3", false);
				start_Hash.put("tDBInput_3", System.currentTimeMillis());

				currentComponent = "tDBInput_3";

				int tos_count_tDBInput_3 = 0;

				int nb_line_tDBInput_3 = 0;
				java.sql.Connection conn_tDBInput_3 = null;
				String driverClass_tDBInput_3 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_3 = java.lang.Class.forName(driverClass_tDBInput_3);
				String dbUser_tDBInput_3 = "postgres";

				final String decryptedPassword_tDBInput_3 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:llMHN05Gz3jixyUj5o9EOJVsliEnaJuLGZdtqpYXbKmWbS/THA==");

				String dbPwd_tDBInput_3 = decryptedPassword_tDBInput_3;

				String url_tDBInput_3 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "GlobalRetailDW";

				conn_tDBInput_3 = java.sql.DriverManager.getConnection(url_tDBInput_3, dbUser_tDBInput_3,
						dbPwd_tDBInput_3);

				conn_tDBInput_3.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_3 = conn_tDBInput_3.createStatement();

				String dbquery_tDBInput_3 = "SELECT \n  \"public\".\"dim_manager\".\"manager_key\", \n  \"public\".\"dim_manager\".\"region\", \n  \"public\".\"dim_man"
						+ "ager\".\"manager_name\", \n  \"public\".\"dim_manager\".\"yearly_target\"\nFROM \"public\".\"dim_manager\"";

				globalMap.put("tDBInput_3_QUERY", dbquery_tDBInput_3);
				java.sql.ResultSet rs_tDBInput_3 = null;

				try {
					rs_tDBInput_3 = stmt_tDBInput_3.executeQuery(dbquery_tDBInput_3);
					java.sql.ResultSetMetaData rsmd_tDBInput_3 = rs_tDBInput_3.getMetaData();
					int colQtyInRs_tDBInput_3 = rsmd_tDBInput_3.getColumnCount();

					String tmpContent_tDBInput_3 = null;

					while (rs_tDBInput_3.next()) {
						nb_line_tDBInput_3++;

						if (colQtyInRs_tDBInput_3 < 1) {
							row_Mgr.manager_key = 0;
						} else {

							row_Mgr.manager_key = rs_tDBInput_3.getInt(1);
							if (rs_tDBInput_3.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_3 < 2) {
							row_Mgr.region = null;
						} else {

							row_Mgr.region = routines.system.JDBCUtil.getString(rs_tDBInput_3, 2, false);
						}
						if (colQtyInRs_tDBInput_3 < 3) {
							row_Mgr.manager_name = null;
						} else {

							row_Mgr.manager_name = routines.system.JDBCUtil.getString(rs_tDBInput_3, 3, false);
						}
						if (colQtyInRs_tDBInput_3 < 4) {
							row_Mgr.yearly_target = null;
						} else {

							row_Mgr.yearly_target = rs_tDBInput_3.getBigDecimal(4);
							if (rs_tDBInput_3.wasNull()) {
								row_Mgr.yearly_target = null;
							}
						}

						/**
						 * [tDBInput_3 begin ] stop
						 */

						/**
						 * [tDBInput_3 main ] start
						 */

						currentComponent = "tDBInput_3";

						tos_count_tDBInput_3++;

						/**
						 * [tDBInput_3 main ] stop
						 */

						/**
						 * [tDBInput_3 process_data_begin ] start
						 */

						currentComponent = "tDBInput_3";

						/**
						 * [tDBInput_3 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row_Mgr main ] start
						 */

						currentComponent = "tAdvancedHash_row_Mgr";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row_Mgr"

							);
						}

						row_MgrStruct row_Mgr_HashRow = new row_MgrStruct();

						row_Mgr_HashRow.manager_key = row_Mgr.manager_key;

						row_Mgr_HashRow.region = row_Mgr.region;

						row_Mgr_HashRow.manager_name = row_Mgr.manager_name;

						row_Mgr_HashRow.yearly_target = row_Mgr.yearly_target;

						tHash_Lookup_row_Mgr.put(row_Mgr_HashRow);

						tos_count_tAdvancedHash_row_Mgr++;

						/**
						 * [tAdvancedHash_row_Mgr main ] stop
						 */

						/**
						 * [tAdvancedHash_row_Mgr process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row_Mgr";

						/**
						 * [tAdvancedHash_row_Mgr process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row_Mgr process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row_Mgr";

						/**
						 * [tAdvancedHash_row_Mgr process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 process_data_end ] start
						 */

						currentComponent = "tDBInput_3";

						/**
						 * [tDBInput_3 process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 end ] start
						 */

						currentComponent = "tDBInput_3";

					}
				} finally {
					if (rs_tDBInput_3 != null) {
						rs_tDBInput_3.close();
					}
					if (stmt_tDBInput_3 != null) {
						stmt_tDBInput_3.close();
					}
					if (conn_tDBInput_3 != null && !conn_tDBInput_3.isClosed()) {

						conn_tDBInput_3.commit();

						conn_tDBInput_3.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}
				globalMap.put("tDBInput_3_NB_LINE", nb_line_tDBInput_3);

				ok_Hash.put("tDBInput_3", true);
				end_Hash.put("tDBInput_3", System.currentTimeMillis());

				/**
				 * [tDBInput_3 end ] stop
				 */

				/**
				 * [tAdvancedHash_row_Mgr end ] start
				 */

				currentComponent = "tAdvancedHash_row_Mgr";

				tHash_Lookup_row_Mgr.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row_Mgr");
				}

				ok_Hash.put("tAdvancedHash_row_Mgr", true);
				end_Hash.put("tAdvancedHash_row_Mgr", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row_Mgr end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_3 finally ] start
				 */

				currentComponent = "tDBInput_3";

				/**
				 * [tDBInput_3 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row_Mgr finally ] start
				 */

				currentComponent = "tAdvancedHash_row_Mgr";

				/**
				 * [tAdvancedHash_row_Mgr finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 1);
	}

	public static class row_ProdStruct implements routines.system.IPersistableComparableLookupRow<row_ProdStruct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int product_key;

		public int getProduct_key() {
			return this.product_key;
		}

		public String product_id_source;

		public String getProduct_id_source() {
			return this.product_id_source;
		}

		public String category;

		public String getCategory() {
			return this.category;
		}

		public String sub_category;

		public String getSub_category() {
			return this.sub_category;
		}

		public String product_name;

		public String getProduct_name() {
			return this.product_name;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.product_id_source == null) ? 0 : this.product_id_source.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row_ProdStruct other = (row_ProdStruct) obj;

			if (this.product_id_source == null) {
				if (other.product_id_source != null)
					return false;

			} else if (!this.product_id_source.equals(other.product_id_source))

				return false;

			return true;
		}

		public void copyDataTo(row_ProdStruct other) {

			other.product_key = this.product_key;
			other.product_id_source = this.product_id_source;
			other.category = this.category;
			other.sub_category = this.sub_category;
			other.product_name = this.product_name;

		}

		public void copyKeysDataTo(row_ProdStruct other) {

			other.product_id_source = this.product_id_source;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.product_id_source = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.product_id_source = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.product_id_source, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.product_id_source, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.product_key = dis.readInt();

				this.category = readString(dis, ois);

				this.sub_category = readString(dis, ois);

				this.product_name = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.product_key = objectIn.readInt();

				this.category = readString(dis, objectIn);

				this.sub_category = readString(dis, objectIn);

				this.product_name = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.product_key);

				writeString(this.category, dos, oos);

				writeString(this.sub_category, dos, oos);

				writeString(this.product_name, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.product_key);

				writeString(this.category, dos, objectOut);

				writeString(this.sub_category, dos, objectOut);

				writeString(this.product_name, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("product_key=" + String.valueOf(product_key));
			sb.append(",product_id_source=" + product_id_source);
			sb.append(",category=" + category);
			sb.append(",sub_category=" + sub_category);
			sb.append(",product_name=" + product_name);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row_ProdStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.product_id_source, other.product_id_source);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row_ProdStruct row_Prod = new row_ProdStruct();

				/**
				 * [tAdvancedHash_row_Prod begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row_Prod", false);
				start_Hash.put("tAdvancedHash_row_Prod", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row_Prod";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row_Prod");
				}

				int tos_count_tAdvancedHash_row_Prod = 0;

				// connection name:row_Prod
				// source node:tDBInput_4 - inputs:(after_tFileInputDelimited_4)
				// outputs:(row_Prod,row_Prod) | target node:tAdvancedHash_row_Prod -
				// inputs:(row_Prod) outputs:()
				// linked node: tMap_5 -
				// inputs:(row_Main,row_Cust,row_Loc,row_Mgr,row_Prod,row_XML)
				// outputs:(Out_LocationF)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row_Prod = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_ProdStruct> tHash_Lookup_row_Prod = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row_ProdStruct>getLookup(matchingModeEnum_row_Prod);

				globalMap.put("tHash_Lookup_row_Prod", tHash_Lookup_row_Prod);

				/**
				 * [tAdvancedHash_row_Prod begin ] stop
				 */

				/**
				 * [tDBInput_4 begin ] start
				 */

				ok_Hash.put("tDBInput_4", false);
				start_Hash.put("tDBInput_4", System.currentTimeMillis());

				currentComponent = "tDBInput_4";

				int tos_count_tDBInput_4 = 0;

				int nb_line_tDBInput_4 = 0;
				java.sql.Connection conn_tDBInput_4 = null;
				String driverClass_tDBInput_4 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_4 = java.lang.Class.forName(driverClass_tDBInput_4);
				String dbUser_tDBInput_4 = "postgres";

				final String decryptedPassword_tDBInput_4 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:Tahel8x+KUPvkySjqGW7Sh5ZTWzCEW+JyYrXlMRe4fNyjOs+Vw==");

				String dbPwd_tDBInput_4 = decryptedPassword_tDBInput_4;

				String url_tDBInput_4 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "GlobalRetailDW";

				conn_tDBInput_4 = java.sql.DriverManager.getConnection(url_tDBInput_4, dbUser_tDBInput_4,
						dbPwd_tDBInput_4);

				conn_tDBInput_4.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_4 = conn_tDBInput_4.createStatement();

				String dbquery_tDBInput_4 = "SELECT \n  \"public\".\"dim_product\".\"product_key\", \n  \"public\".\"dim_product\".\"product_id_source\", \n  \"public"
						+ "\".\"dim_product\".\"category\", \n  \"public\".\"dim_product\".\"sub_category\", \n  \"public\".\"dim_product\".\"product"
						+ "_name\"\nFROM \"public\".\"dim_product\"";

				globalMap.put("tDBInput_4_QUERY", dbquery_tDBInput_4);
				java.sql.ResultSet rs_tDBInput_4 = null;

				try {
					rs_tDBInput_4 = stmt_tDBInput_4.executeQuery(dbquery_tDBInput_4);
					java.sql.ResultSetMetaData rsmd_tDBInput_4 = rs_tDBInput_4.getMetaData();
					int colQtyInRs_tDBInput_4 = rsmd_tDBInput_4.getColumnCount();

					String tmpContent_tDBInput_4 = null;

					while (rs_tDBInput_4.next()) {
						nb_line_tDBInput_4++;

						if (colQtyInRs_tDBInput_4 < 1) {
							row_Prod.product_key = 0;
						} else {

							row_Prod.product_key = rs_tDBInput_4.getInt(1);
							if (rs_tDBInput_4.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_4 < 2) {
							row_Prod.product_id_source = null;
						} else {

							row_Prod.product_id_source = routines.system.JDBCUtil.getString(rs_tDBInput_4, 2, false);
						}
						if (colQtyInRs_tDBInput_4 < 3) {
							row_Prod.category = null;
						} else {

							row_Prod.category = routines.system.JDBCUtil.getString(rs_tDBInput_4, 3, false);
						}
						if (colQtyInRs_tDBInput_4 < 4) {
							row_Prod.sub_category = null;
						} else {

							row_Prod.sub_category = routines.system.JDBCUtil.getString(rs_tDBInput_4, 4, false);
						}
						if (colQtyInRs_tDBInput_4 < 5) {
							row_Prod.product_name = null;
						} else {

							row_Prod.product_name = routines.system.JDBCUtil.getString(rs_tDBInput_4, 5, false);
						}

						/**
						 * [tDBInput_4 begin ] stop
						 */

						/**
						 * [tDBInput_4 main ] start
						 */

						currentComponent = "tDBInput_4";

						tos_count_tDBInput_4++;

						/**
						 * [tDBInput_4 main ] stop
						 */

						/**
						 * [tDBInput_4 process_data_begin ] start
						 */

						currentComponent = "tDBInput_4";

						/**
						 * [tDBInput_4 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row_Prod main ] start
						 */

						currentComponent = "tAdvancedHash_row_Prod";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row_Prod"

							);
						}

						row_ProdStruct row_Prod_HashRow = new row_ProdStruct();

						row_Prod_HashRow.product_key = row_Prod.product_key;

						row_Prod_HashRow.product_id_source = row_Prod.product_id_source;

						row_Prod_HashRow.category = row_Prod.category;

						row_Prod_HashRow.sub_category = row_Prod.sub_category;

						row_Prod_HashRow.product_name = row_Prod.product_name;

						tHash_Lookup_row_Prod.put(row_Prod_HashRow);

						tos_count_tAdvancedHash_row_Prod++;

						/**
						 * [tAdvancedHash_row_Prod main ] stop
						 */

						/**
						 * [tAdvancedHash_row_Prod process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row_Prod";

						/**
						 * [tAdvancedHash_row_Prod process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row_Prod process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row_Prod";

						/**
						 * [tAdvancedHash_row_Prod process_data_end ] stop
						 */

						/**
						 * [tDBInput_4 process_data_end ] start
						 */

						currentComponent = "tDBInput_4";

						/**
						 * [tDBInput_4 process_data_end ] stop
						 */

						/**
						 * [tDBInput_4 end ] start
						 */

						currentComponent = "tDBInput_4";

					}
				} finally {
					if (rs_tDBInput_4 != null) {
						rs_tDBInput_4.close();
					}
					if (stmt_tDBInput_4 != null) {
						stmt_tDBInput_4.close();
					}
					if (conn_tDBInput_4 != null && !conn_tDBInput_4.isClosed()) {

						conn_tDBInput_4.commit();

						conn_tDBInput_4.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}
				globalMap.put("tDBInput_4_NB_LINE", nb_line_tDBInput_4);

				ok_Hash.put("tDBInput_4", true);
				end_Hash.put("tDBInput_4", System.currentTimeMillis());

				/**
				 * [tDBInput_4 end ] stop
				 */

				/**
				 * [tAdvancedHash_row_Prod end ] start
				 */

				currentComponent = "tAdvancedHash_row_Prod";

				tHash_Lookup_row_Prod.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row_Prod");
				}

				ok_Hash.put("tAdvancedHash_row_Prod", true);
				end_Hash.put("tAdvancedHash_row_Prod", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row_Prod end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_4 finally ] start
				 */

				currentComponent = "tDBInput_4";

				/**
				 * [tDBInput_4 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row_Prod finally ] start
				 */

				currentComponent = "tAdvancedHash_row_Prod";

				/**
				 * [tAdvancedHash_row_Prod finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 1);
	}

	public static class row_XMLStruct implements routines.system.IPersistableComparableLookupRow<row_XMLStruct> {
		final static byte[] commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		static byte[] commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String OrderID;

		public String getOrderID() {
			return this.OrderID;
		}

		public String Status;

		public String getStatus() {
			return this.Status;
		}

		public String Reason;

		public String getReason() {
			return this.Reason;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.OrderID == null) ? 0 : this.OrderID.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row_XMLStruct other = (row_XMLStruct) obj;

			if (this.OrderID == null) {
				if (other.OrderID != null)
					return false;

			} else if (!this.OrderID.equals(other.OrderID))

				return false;

			return true;
		}

		public void copyDataTo(row_XMLStruct other) {

			other.OrderID = this.OrderID;
			other.Status = this.Status;
			other.Reason = this.Reason;

		}

		public void copyKeysDataTo(row_XMLStruct other) {

			other.OrderID = this.OrderID;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length) {
					if (length < 1024 && commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load.length == 0) {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[1024];
					} else {
						commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length);
				strReturn = new String(commonByteArray_GLOBALRETAILDW_Job_GlobalRetail_Load, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.OrderID = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_GLOBALRETAILDW_Job_GlobalRetail_Load) {

				try {

					int length = 0;

					this.OrderID = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.OrderID, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.OrderID, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.Status = readString(dis, ois);

				this.Reason = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.Status = readString(dis, objectIn);

				this.Reason = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeString(this.Status, dos, oos);

				writeString(this.Reason, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeString(this.Status, dos, objectOut);

				writeString(this.Reason, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("OrderID=" + OrderID);
			sb.append(",Status=" + Status);
			sb.append(",Reason=" + Reason);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row_XMLStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.OrderID, other.OrderID);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputXML_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputXML_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row_XMLStruct row_XML = new row_XMLStruct();

				/**
				 * [tAdvancedHash_row_XML begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row_XML", false);
				start_Hash.put("tAdvancedHash_row_XML", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row_XML";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row_XML");
				}

				int tos_count_tAdvancedHash_row_XML = 0;

				// connection name:row_XML
				// source node:tFileInputXML_1 - inputs:(after_tFileInputDelimited_4)
				// outputs:(row_XML,row_XML) | target node:tAdvancedHash_row_XML -
				// inputs:(row_XML) outputs:()
				// linked node: tMap_5 -
				// inputs:(row_Main,row_Cust,row_Loc,row_Mgr,row_Prod,row_XML)
				// outputs:(Out_LocationF)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row_XML = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row_XMLStruct> tHash_Lookup_row_XML = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row_XMLStruct>getLookup(matchingModeEnum_row_XML);

				globalMap.put("tHash_Lookup_row_XML", tHash_Lookup_row_XML);

				/**
				 * [tAdvancedHash_row_XML begin ] stop
				 */

				/**
				 * [tFileInputXML_1 begin ] start
				 */

				ok_Hash.put("tFileInputXML_1", false);
				start_Hash.put("tFileInputXML_1", System.currentTimeMillis());

				currentComponent = "tFileInputXML_1";

				int tos_count_tFileInputXML_1 = 0;

				int nb_line_tFileInputXML_1 = 0;

				String os_tFileInputXML_1 = System.getProperty("os.name").toLowerCase();
				boolean isWindows_tFileInputXML_1 = false;
				if (os_tFileInputXML_1.indexOf("windows") > -1 || os_tFileInputXML_1.indexOf("nt") > -1) {
					isWindows_tFileInputXML_1 = true;
				}
				class NameSpaceTool_tFileInputXML_1 {

					public java.util.HashMap<String, String> xmlNameSpaceMap = new java.util.HashMap<String, String>();

					private java.util.List<String> defualtNSPath = new java.util.ArrayList<String>();

					public void countNSMap(org.dom4j.Element el) {
						for (org.dom4j.Namespace ns : (java.util.List<org.dom4j.Namespace>) el.declaredNamespaces()) {
							if (ns.getPrefix().trim().length() == 0) {
								xmlNameSpaceMap.put("pre" + defualtNSPath.size(), ns.getURI());
								String path = "";
								org.dom4j.Element elTmp = el;
								while (elTmp != null) {
									if (elTmp.getNamespacePrefix() != null && elTmp.getNamespacePrefix().length() > 0) {
										path = "/" + elTmp.getNamespacePrefix() + ":" + elTmp.getName() + path;
									} else {
										path = "/" + elTmp.getName() + path;
									}
									elTmp = elTmp.getParent();
								}
								defualtNSPath.add(path);
							} else {
								xmlNameSpaceMap.put(ns.getPrefix(), ns.getURI());
							}

						}
						for (org.dom4j.Element e : (java.util.List<org.dom4j.Element>) el.elements()) {
							countNSMap(e);
						}
					}

					private final org.talend.xpath.XPathUtil util = new org.talend.xpath.XPathUtil();

					{
						util.setDefaultNSPath(defualtNSPath);
					}

					public String addDefaultNSPrefix(String path) {
						return util.addDefaultNSPrefix(path);
					}

					public String addDefaultNSPrefix(String relativeXpression, String basePath) {
						return util.addDefaultNSPrefix(relativeXpression, basePath);
					}

				}

				class XML_API_tFileInputXML_1 {
					public boolean isDefNull(org.dom4j.Node node) throws javax.xml.transform.TransformerException {
						if (node != null && node instanceof org.dom4j.Element) {
							org.dom4j.Attribute attri = ((org.dom4j.Element) node).attribute("nil");
							if (attri != null && ("true").equals(attri.getText())) {
								return true;
							}
						}
						return false;
					}

					public boolean isMissing(org.dom4j.Node node) throws javax.xml.transform.TransformerException {
						return node == null ? true : false;
					}

					public boolean isEmpty(org.dom4j.Node node) throws javax.xml.transform.TransformerException {
						if (node != null) {
							return node.getText().length() == 0;
						}
						return false;
					}
				}

				org.dom4j.io.SAXReader reader_tFileInputXML_1 = new org.dom4j.io.SAXReader();
				Object filename_tFileInputXML_1 = null;
				try {
					filename_tFileInputXML_1 = "C:/Users/DELL/Desktop/GlobalRetail_BI_360/data/generated_sources/Source_Logistique_Retours.xml";
				} catch (java.lang.Exception e) {
					globalMap.put("tFileInputXML_1_ERROR_MESSAGE", e.getMessage());

					System.err.println(e.getMessage());

				}
				if (filename_tFileInputXML_1 != null && filename_tFileInputXML_1 instanceof String
						&& filename_tFileInputXML_1.toString().startsWith("//")) {
					if (!isWindows_tFileInputXML_1) {
						filename_tFileInputXML_1 = filename_tFileInputXML_1.toString().replaceFirst("//", "/");
					}
				}

				boolean isValidFile_tFileInputXML_1 = true;
				org.dom4j.Document doc_tFileInputXML_1 = null;
				java.io.Closeable toClose_tFileInputXML_1 = null;
				try {
					if (filename_tFileInputXML_1 instanceof java.io.InputStream) {
						java.io.InputStream inputStream_tFileInputXML_1 = (java.io.InputStream) filename_tFileInputXML_1;
						toClose_tFileInputXML_1 = inputStream_tFileInputXML_1;
						doc_tFileInputXML_1 = reader_tFileInputXML_1.read(inputStream_tFileInputXML_1);
					} else {
						java.io.Reader unicodeReader_tFileInputXML_1 = new UnicodeReader(
								new java.io.FileInputStream(String.valueOf(filename_tFileInputXML_1)), "UTF-8");
						toClose_tFileInputXML_1 = unicodeReader_tFileInputXML_1;
						org.xml.sax.InputSource in_tFileInputXML_1 = new org.xml.sax.InputSource(
								unicodeReader_tFileInputXML_1);
						doc_tFileInputXML_1 = reader_tFileInputXML_1.read(in_tFileInputXML_1);
					}
				} catch (java.lang.Exception e) {
					globalMap.put("tFileInputXML_1_ERROR_MESSAGE", e.getMessage());

					System.err.println(e.getMessage());
					isValidFile_tFileInputXML_1 = false;
				} finally {
					if (toClose_tFileInputXML_1 != null) {
						toClose_tFileInputXML_1.close();
					}
				}
				if (isValidFile_tFileInputXML_1) {
					NameSpaceTool_tFileInputXML_1 nsTool_tFileInputXML_1 = new NameSpaceTool_tFileInputXML_1();
					nsTool_tFileInputXML_1.countNSMap(doc_tFileInputXML_1.getRootElement());
					java.util.HashMap<String, String> xmlNameSpaceMap_tFileInputXML_1 = nsTool_tFileInputXML_1.xmlNameSpaceMap;

					org.dom4j.XPath x_tFileInputXML_1 = doc_tFileInputXML_1
							.createXPath(nsTool_tFileInputXML_1.addDefaultNSPrefix("/Returns/Return"));
					x_tFileInputXML_1.setNamespaceURIs(xmlNameSpaceMap_tFileInputXML_1);

					java.util.List<org.dom4j.Node> nodeList_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) x_tFileInputXML_1
							.selectNodes(doc_tFileInputXML_1);
					XML_API_tFileInputXML_1 xml_api_tFileInputXML_1 = new XML_API_tFileInputXML_1();
					String str_tFileInputXML_1 = "";
					org.dom4j.Node node_tFileInputXML_1 = null;

//init all mapping xpaths
					java.util.Map<Integer, org.dom4j.XPath> xpaths_tFileInputXML_1 = new java.util.HashMap<Integer, org.dom4j.XPath>();
					class XPathUtil_tFileInputXML_1 {

						public void initXPaths_0(java.util.Map<Integer, org.dom4j.XPath> xpaths,
								NameSpaceTool_tFileInputXML_1 nsTool,
								java.util.HashMap<String, String> xmlNameSpaceMap) {

							org.dom4j.XPath xpath_0 = org.dom4j.DocumentHelper
									.createXPath(nsTool.addDefaultNSPrefix("OrderID", "/Returns/Return"));
							xpath_0.setNamespaceURIs(xmlNameSpaceMap);

							xpaths.put(0, xpath_0);

							org.dom4j.XPath xpath_1 = org.dom4j.DocumentHelper
									.createXPath(nsTool.addDefaultNSPrefix("Status", "/Returns/Return"));
							xpath_1.setNamespaceURIs(xmlNameSpaceMap);

							xpaths.put(1, xpath_1);

							org.dom4j.XPath xpath_2 = org.dom4j.DocumentHelper
									.createXPath(nsTool.addDefaultNSPrefix("Reason", "/Returns/Return"));
							xpath_2.setNamespaceURIs(xmlNameSpaceMap);

							xpaths.put(2, xpath_2);

						}

						public void initXPaths(java.util.Map<Integer, org.dom4j.XPath> xpaths,
								NameSpaceTool_tFileInputXML_1 nsTool,
								java.util.HashMap<String, String> xmlNameSpaceMap) {

							initXPaths_0(xpaths, nsTool, xmlNameSpaceMap);

						}
					}
					XPathUtil_tFileInputXML_1 xPathUtil_tFileInputXML_1 = new XPathUtil_tFileInputXML_1();
					xPathUtil_tFileInputXML_1.initXPaths(xpaths_tFileInputXML_1, nsTool_tFileInputXML_1,
							xmlNameSpaceMap_tFileInputXML_1);
					for (org.dom4j.Node temp_tFileInputXML_1 : nodeList_tFileInputXML_1) {
						if (nb_line_tFileInputXML_1 >= 50) {

							break;
						}
						nb_line_tFileInputXML_1++;

						row_XML = null;
						row_XML = null;
						boolean whetherReject_tFileInputXML_1 = false;
						row_XML = new row_XMLStruct();
						try {
							Object obj0_tFileInputXML_1 = xpaths_tFileInputXML_1.get(0).evaluate(temp_tFileInputXML_1);
							if (obj0_tFileInputXML_1 == null) {
								node_tFileInputXML_1 = null;
								str_tFileInputXML_1 = "";

							} else if (obj0_tFileInputXML_1 instanceof org.dom4j.Node) {
								node_tFileInputXML_1 = (org.dom4j.Node) obj0_tFileInputXML_1;
								str_tFileInputXML_1 = org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
										org.jaxen.dom4j.DocumentNavigator.getInstance());
							} else if (obj0_tFileInputXML_1 instanceof String
									|| obj0_tFileInputXML_1 instanceof Number) {
								node_tFileInputXML_1 = temp_tFileInputXML_1;
								str_tFileInputXML_1 = String.valueOf(obj0_tFileInputXML_1);
							} else if (obj0_tFileInputXML_1 instanceof java.util.List) {
								java.util.List<org.dom4j.Node> nodes_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) obj0_tFileInputXML_1;
								node_tFileInputXML_1 = nodes_tFileInputXML_1.size() > 0 ? nodes_tFileInputXML_1.get(0)
										: null;
								str_tFileInputXML_1 = node_tFileInputXML_1 == null ? ""
										: org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
												org.jaxen.dom4j.DocumentNavigator.getInstance());
							}
							if (xml_api_tFileInputXML_1.isDefNull(node_tFileInputXML_1)) {
								row_XML.OrderID = null;
							} else if (xml_api_tFileInputXML_1.isEmpty(node_tFileInputXML_1)) {
								row_XML.OrderID = "";
							} else if (xml_api_tFileInputXML_1.isMissing(node_tFileInputXML_1)) {
								row_XML.OrderID = null;
							} else {
								row_XML.OrderID = str_tFileInputXML_1;
							}
							Object obj1_tFileInputXML_1 = xpaths_tFileInputXML_1.get(1).evaluate(temp_tFileInputXML_1);
							if (obj1_tFileInputXML_1 == null) {
								node_tFileInputXML_1 = null;
								str_tFileInputXML_1 = "";

							} else if (obj1_tFileInputXML_1 instanceof org.dom4j.Node) {
								node_tFileInputXML_1 = (org.dom4j.Node) obj1_tFileInputXML_1;
								str_tFileInputXML_1 = org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
										org.jaxen.dom4j.DocumentNavigator.getInstance());
							} else if (obj1_tFileInputXML_1 instanceof String
									|| obj1_tFileInputXML_1 instanceof Number) {
								node_tFileInputXML_1 = temp_tFileInputXML_1;
								str_tFileInputXML_1 = String.valueOf(obj1_tFileInputXML_1);
							} else if (obj1_tFileInputXML_1 instanceof java.util.List) {
								java.util.List<org.dom4j.Node> nodes_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) obj1_tFileInputXML_1;
								node_tFileInputXML_1 = nodes_tFileInputXML_1.size() > 0 ? nodes_tFileInputXML_1.get(0)
										: null;
								str_tFileInputXML_1 = node_tFileInputXML_1 == null ? ""
										: org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
												org.jaxen.dom4j.DocumentNavigator.getInstance());
							}
							if (xml_api_tFileInputXML_1.isDefNull(node_tFileInputXML_1)) {
								row_XML.Status = null;
							} else if (xml_api_tFileInputXML_1.isEmpty(node_tFileInputXML_1)) {
								row_XML.Status = "";
							} else if (xml_api_tFileInputXML_1.isMissing(node_tFileInputXML_1)) {
								row_XML.Status = null;
							} else {
								row_XML.Status = str_tFileInputXML_1;
							}
							Object obj2_tFileInputXML_1 = xpaths_tFileInputXML_1.get(2).evaluate(temp_tFileInputXML_1);
							if (obj2_tFileInputXML_1 == null) {
								node_tFileInputXML_1 = null;
								str_tFileInputXML_1 = "";

							} else if (obj2_tFileInputXML_1 instanceof org.dom4j.Node) {
								node_tFileInputXML_1 = (org.dom4j.Node) obj2_tFileInputXML_1;
								str_tFileInputXML_1 = org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
										org.jaxen.dom4j.DocumentNavigator.getInstance());
							} else if (obj2_tFileInputXML_1 instanceof String
									|| obj2_tFileInputXML_1 instanceof Number) {
								node_tFileInputXML_1 = temp_tFileInputXML_1;
								str_tFileInputXML_1 = String.valueOf(obj2_tFileInputXML_1);
							} else if (obj2_tFileInputXML_1 instanceof java.util.List) {
								java.util.List<org.dom4j.Node> nodes_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) obj2_tFileInputXML_1;
								node_tFileInputXML_1 = nodes_tFileInputXML_1.size() > 0 ? nodes_tFileInputXML_1.get(0)
										: null;
								str_tFileInputXML_1 = node_tFileInputXML_1 == null ? ""
										: org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
												org.jaxen.dom4j.DocumentNavigator.getInstance());
							}
							if (xml_api_tFileInputXML_1.isDefNull(node_tFileInputXML_1)) {
								row_XML.Reason = null;
							} else if (xml_api_tFileInputXML_1.isEmpty(node_tFileInputXML_1)) {
								row_XML.Reason = "";
							} else if (xml_api_tFileInputXML_1.isMissing(node_tFileInputXML_1)) {
								row_XML.Reason = null;
							} else {
								row_XML.Reason = str_tFileInputXML_1;
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputXML_1_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputXML_1 = true;
							System.err.println(e.getMessage());
							row_XML = null;
						}

						/**
						 * [tFileInputXML_1 begin ] stop
						 */

						/**
						 * [tFileInputXML_1 main ] start
						 */

						currentComponent = "tFileInputXML_1";

						tos_count_tFileInputXML_1++;

						/**
						 * [tFileInputXML_1 main ] stop
						 */

						/**
						 * [tFileInputXML_1 process_data_begin ] start
						 */

						currentComponent = "tFileInputXML_1";

						/**
						 * [tFileInputXML_1 process_data_begin ] stop
						 */
// Start of branch "row_XML"
						if (row_XML != null) {

							/**
							 * [tAdvancedHash_row_XML main ] start
							 */

							currentComponent = "tAdvancedHash_row_XML";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row_XML"

								);
							}

							row_XMLStruct row_XML_HashRow = new row_XMLStruct();

							row_XML_HashRow.OrderID = row_XML.OrderID;

							row_XML_HashRow.Status = row_XML.Status;

							row_XML_HashRow.Reason = row_XML.Reason;

							tHash_Lookup_row_XML.put(row_XML_HashRow);

							tos_count_tAdvancedHash_row_XML++;

							/**
							 * [tAdvancedHash_row_XML main ] stop
							 */

							/**
							 * [tAdvancedHash_row_XML process_data_begin ] start
							 */

							currentComponent = "tAdvancedHash_row_XML";

							/**
							 * [tAdvancedHash_row_XML process_data_begin ] stop
							 */

							/**
							 * [tAdvancedHash_row_XML process_data_end ] start
							 */

							currentComponent = "tAdvancedHash_row_XML";

							/**
							 * [tAdvancedHash_row_XML process_data_end ] stop
							 */

						} // End of branch "row_XML"

						/**
						 * [tFileInputXML_1 process_data_end ] start
						 */

						currentComponent = "tFileInputXML_1";

						/**
						 * [tFileInputXML_1 process_data_end ] stop
						 */

						/**
						 * [tFileInputXML_1 end ] start
						 */

						currentComponent = "tFileInputXML_1";

					}
				}
				globalMap.put("tFileInputXML_1_NB_LINE", nb_line_tFileInputXML_1);

				ok_Hash.put("tFileInputXML_1", true);
				end_Hash.put("tFileInputXML_1", System.currentTimeMillis());

				/**
				 * [tFileInputXML_1 end ] stop
				 */

				/**
				 * [tAdvancedHash_row_XML end ] start
				 */

				currentComponent = "tAdvancedHash_row_XML";

				tHash_Lookup_row_XML.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row_XML");
				}

				ok_Hash.put("tAdvancedHash_row_XML", true);
				end_Hash.put("tAdvancedHash_row_XML", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row_XML end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputXML_1 finally ] start
				 */

				currentComponent = "tFileInputXML_1";

				/**
				 * [tFileInputXML_1 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row_XML finally ] start
				 */

				currentComponent = "tAdvancedHash_row_XML";

				/**
				 * [tAdvancedHash_row_XML finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputXML_1_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final Job_GlobalRetail_Load Job_GlobalRetail_LoadClass = new Job_GlobalRetail_Load();

		int exitCode = Job_GlobalRetail_LoadClass.runJobInTOS(args);

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (inOSGi) {
			java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

			if (jobProperties != null && jobProperties.get("context") != null) {
				contextStr = (String) jobProperties.get("context");
			}
		}

		try {
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = Job_GlobalRetail_Load.class.getClassLoader().getResourceAsStream(
					"globalretaildw/job_globalretail_load_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = Job_GlobalRetail_Load.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tFileInputDelimited_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_1) {
			globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println(
					(endUsedMemory - startUsedMemory) + " bytes memory increase when running : Job_GlobalRetail_Load");
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 611440 characters generated by Talend Open Studio for Data Integration on the
 * 14 janvier 2026 à 18:52:07 WEST
 ************************************************************************************************/