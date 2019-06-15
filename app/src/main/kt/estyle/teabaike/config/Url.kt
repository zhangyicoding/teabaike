package estyle.teabaike.config

object Url {

    const val BASE_URL = "http://sns.maimaicha.com/"

    val TITLES = arrayOf("头条", "百科", "咨询", "经营", "数据")
    val TYPES = arrayOf(TITLES[0], "16", "52", "53", "54")

    // 频道接口
    const val CHANNEL_URL =
        "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&row=10"
    // 频道类型
    const val TYPE = "type"
    // 分页
    const val PAGE = "page"


    //首页幻灯片数据路径
    const val HEADLINE_HEADER_URL =
        "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow"

    //头条数据
    const val HEADLINE_URL =
        "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&row=10"

    //内容详情页，id为对应文章id，主界面Json数据中获取
    const val CONTENT_URL =
        "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent"
    const val ID = "id"

    //搜索
    const val SEARCH_URL =
        "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.searcListByTitle&rows=10"
    const val SEARCH = "search"

    // 意见反馈
    const val FEEDBACK_URL =
        "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=system.feedback"
    const val FEEDBACK_TITLE = "title"
    const val FEEDBACK_CONTENT = "content"

    // 检查新版本
    const val CHECK_VERSION_URL =
        "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=latestVersion"
}
