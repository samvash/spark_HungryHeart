
import com.otmu.common.config.OtmuSparkConfig
import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor}
import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.SparkContext

object HbaseIntegration {

  val sc = new SparkContext(new OtmuSparkConfig().getWindowsSparkConfig("hbase integration"))
  val input_table = "otmu_registrations"
  val conf = HBaseConfiguration.create()
  conf.set(TableInputFormat.INPUT_TABLE, input_table)
  // Initialize hBase table if necessary
  val admin = new HBaseAdmin(conf)
  if(!admin.isTableAvailable(input_table)) {
    val tableDesc = new HTableDescriptor(input_table)
    admin.createTable(tableDesc)
  }
  val hBaseRDD = sc.newAPIHadoopRDD(conf,
    classOf[TableInputFormat],
    classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
    classOf[org.apache.hadoop.hbase.client.Result])

}
