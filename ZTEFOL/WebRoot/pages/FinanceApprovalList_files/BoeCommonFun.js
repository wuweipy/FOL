/* 引用拆分后的各个JS文件 */
document.write(" <script language=\"javascript\" src=\"..\/..\/Common\/Js\/BoeLine1.js\"><\/script> ");
document.write(" <script language=\"javascript\" src=\"..\/..\/Common\/Js\/BoeLine2.js\"><\/script> ");
document.write(" <script language=\"javascript\" src=\"..\/..\/Common\/Js\/BoeLine3.js\"><\/script> ");
document.write(" <script language=\"javascript\" src=\"..\/..\/Common\/Js\/BoeLine4.js\"><\/script> ");
/* 为了性能，不加文件头 */
var shouldChooseProject='4001002,4001003,4001001,4002001';
var shouldChooseAolContract = false;// 是否必须选择租赁合同
var errCommCode=new Array("~" , "'" , "!" , "@" , "#" , "$" , "%" , "*" , "_" , "=" , "{" , "}" , "[" , "]" , "|" , "\\" , ";" , "\"" , "<" , ">" , "&");//2012-03-08 史蕾 添加 海外本地支付、香港代付：收款行国家/城市、汇款用途 不能有的特殊字符
 