package com.nmssdmf.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * 时间格式管理类
 */
class DateFormatUtil {
    companion object {
        @JvmStatic
        val DATE_FORMAT_1:String = "yyyy-MM-dd HH:mm:ss"
        @JvmStatic
        val DATE_FORMAT_2:String = "yyyy-MM-dd"
        @JvmStatic
        val DATE_FORMAT_3:String = "yyyy年MM月dd日"
        @JvmStatic
        val DATE_FORMAT_4:String = "hh:mm"
        @JvmStatic
        val DATE_FORMAT_5:String = "HH:mm"

        /***
         * 时间戳转换
         */
        @JvmStatic
        fun getDateFormatString(milliseconds: Long, formartStr:String = DATE_FORMAT_1):String{
            var resultD = ""
            val format = SimpleDateFormat(formartStr)
            try {
                val d = Date(milliseconds)
                resultD = format.format(d)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return resultD
        }

        /**
         * 返回星期几
         */
        @JvmStatic
        fun getWeekOfDate(milliseconds: Long): String {
            val weekDaysName =
                arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
            val calendar = Calendar.getInstance()
            val date = Date(milliseconds)
            calendar.time = date
            val intWeek = calendar[Calendar.DAY_OF_WEEK] - 1
            return weekDaysName[intWeek]
        }

        /**
         * 根据不同时间段，显示不同时间
         *
         * @param date
         * @return
         */
        @JvmStatic
        fun getTodayTimeBucket(milliseconds:Long): String {
            val calendar = Calendar.getInstance()
            val date = Date(milliseconds)
            calendar.time = date
            val timeFormat = SimpleDateFormat(DATE_FORMAT_4, Locale.getDefault())
            return when (calendar[Calendar.HOUR_OF_DAY]) {
                in 0..4 -> {
                    "凌晨 " + timeFormat.format(date)
                }

                in 5..11 -> {
                    "上午 " + timeFormat.format(date)
                }

                in 12..17 -> {
                    "下午 " + timeFormat.format(date)
                }

                in 18..23 -> {
                    "晚上 " + timeFormat.format(date)
                }

                else -> ""
            }
        }

        /**
         * 判断两个日期是否在同一周
         *
         * @param date1
         * @param date2
         * @return
         */
        @JvmStatic
        fun isSameWeekDates(milliseconds1: Long, milliseconds2: Long): Boolean {
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.time = Date(milliseconds1)
            cal2.time = Date(milliseconds2)
            val subYear = cal1[Calendar.YEAR] - cal2[Calendar.YEAR]
            if (0 == subYear) {
                if (cal1[Calendar.WEEK_OF_YEAR] == cal2[Calendar.WEEK_OF_YEAR]) return true
            } else if (1 == subYear && 11 == cal2[Calendar.MONTH]) {
                // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
                if (cal1[Calendar.WEEK_OF_YEAR] == cal2[Calendar.WEEK_OF_YEAR]) return true
            } else if (-1 == subYear && 11 == cal1[Calendar.MONTH]) {
                if (cal1[Calendar.WEEK_OF_YEAR] == cal2[Calendar.WEEK_OF_YEAR]) return true
            }
            return false
        }

        /**
         * 获取指定格式的时间
         */
        @JvmStatic
        fun getTimeShowString(milliseconds: Long, abbreviate: Boolean = false): String {
            val dataString: String
            val timeStringBy24: String
            val date = Date(milliseconds)
            val todayStart = Calendar.getInstance()
            todayStart[Calendar.HOUR_OF_DAY] = 0
            todayStart[Calendar.MINUTE] = 0
            todayStart[Calendar.SECOND] = 0
            todayStart[Calendar.MILLISECOND] = 0
            val todayBegin = todayStart.time
            val yesterdayBegin = Date(todayBegin.time - 3600 * 24 * 1000)
            val preyesterday = Date(yesterdayBegin.time - 3600 * 24 * 1000)
            dataString = if (!date.before(todayBegin)) {
                "今天"
            } else if (!date.before(yesterdayBegin)) {
                "昨天"
            } else if (!date.before(preyesterday)) {
                "前天"
            } else if (isSameWeekDates(milliseconds, System.currentTimeMillis())) {
                getWeekOfDate(milliseconds)
            } else {
                val dateFormatter = SimpleDateFormat(DATE_FORMAT_2, Locale.getDefault())
                dateFormatter.format(date)
            }
            val timeFormatter24 = SimpleDateFormat(DATE_FORMAT_5, Locale.getDefault())
            timeStringBy24 = timeFormatter24.format(date)
            return if (abbreviate) {
                if (!date.before(todayBegin)) {
                    getTodayTimeBucket(milliseconds)
                } else {
                    dataString
                }
            } else {
                "$dataString $timeStringBy24"
            }
        }

    }
}