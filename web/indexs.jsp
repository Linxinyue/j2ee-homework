<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.jsp.JspWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<base href=" <%=basePath%>">

<head>
  <meta charset="utf-8">
  <title>
    <s:if test="#session.className == 'remen' ">
      热点
    </s:if>
    <s:elseif test="#session.className == 'shehui' ">
      社会
    </s:elseif>
    <s:elseif test="#session.className == 'yule' ">
      娱乐
    </s:elseif>
    <s:elseif test="#session.className == 'caijing' ">
      财经
    </s:elseif>
    <s:elseif test="#session.className == 'junshi' ">
      军事
    </s:elseif>
    <s:elseif test="#session.className == 'guoji' ">
      国际
    </s:elseif>
    <s:elseif test="#session.className == 'shishang' ">
      时尚
    </s:elseif>
    <s:elseif test="#session.className == 'tansuo' ">
      探索
    </s:elseif>
    <s:elseif test="#session.className == 'meiwen' ">
      美文
    </s:elseif>
    <s:elseif test="#session.className == 'lishi' ">
      历史
    </s:elseif>
    <s:elseif test="#session.className == 'gushi' ">
      故事
    </s:elseif>
    <s:elseif test="#session.className == 'keji' ">
      科技
    </s:elseif>
    <s:elseif test="#session.className == 'youxi' ">
      游戏
    </s:elseif>
    <s:else>
      推荐
    </s:else>
    <%--<s:property value="#session.className"></s:property>--%>
  </title>
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="./style/index.css">
</head>

<body>

<!-- 网站LOGO和主要入口 -->
<s:include value="header.jsp" />

<!-- 文章分类管理及搜索 -->
<div class="mianBar">
  <div class="mianOptions">
    <div class="entries">
      <div class="displayEntry">
        <div class="singleEntry" id="indexEntry">推荐</div>
        <s:if test="#session.myClassifications == null">
          <script>location.reload()</script>
        </s:if>
        <s:iterator value="#session.myClassifications" var="classifi" status="status">
          <s:if test="#status.index < 4">
            <div class="singleEntry"><s:property /></div>
          </s:if>
        </s:iterator>
      </div>

      <div class="singleEntry" onselectstart='return false' id="choose">其他</div>
      <div class="others">
        <s:iterator value="#session.myClassifications" var="classifi" status="status">
          <s:if test="#status.index > 4">
            <div class="otherEntry"><s:property /></div>
          </s:if>
        </s:iterator>
      </div>
    </div>
    <div class="search">
      <form method="get">
        <div class="inputDiv">
          <input class="inputSearch" placeholder="请输入搜索的内容" type="text" name="wd" />
          <div class="searchIcon">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- 正页面 -->
<div class="mianPage" id="mianPage">

  <!-- 文章列表 -->
  <div class="articles">

    <!-- 单个页面系列 -->
    <s:iterator value="#session.articles" var="myArticles" status="status">
      <s:if test="#myArticles[3].toString() != ''">
        <div class="singlePicArticle" id="${myArticles[0]}"><div class="tiltePicDiv"><img class="titlePic" src="${myArticles[3]}" /></div><div class="title"><h4>${myArticles[1]}</h4></div><div class="content">${myArticles[2]}</div><div class="fct">${myArticles[4]}</div><div class="fct">　·　${myArticles[6]}条评论　·　</div><div class="fct">${myArticles[5].toString().substring(0,10)}</div>
          <s:if test="#session.currentUser != null">
            <s:if test="#myArticles[12] != null && #myArticles[12] == 1">
              <div class="lds">${myArticles[9]}<img title="yishoucang" class="useragree" src="img/iconfont-icon2.png"></div>
            </s:if>
            <s:else>
              <div class="lds">${myArticles[9]}<img title="shoucang" class="useragree" src="img/iconfont-icon.png"></div>
            </s:else>

            <s:if test="#myArticles[11] != null && #myArticles[11] == 1">
              <div class="lds">${myArticles[8]}<img class="useragree" title="yicai" src="img/iconfont-zanzan-copy2.png"></div>
            </s:if>
            <s:else>
              <div class="lds">${myArticles[8]}<img class="useragree" title="cai" src="img/iconfont-zanzan-copy.png"></div>
            </s:else>

            <s:if test="#myArticles[10] != null && #myArticles[10] == 1">
              <div class="lds">${myArticles[7]}<img class="useragree" title="yizan" src="img/iconfont-zanzan2.png"></div>
            </s:if>
            <s:else>
              <div class="lds">${myArticles[7]}<img class="useragree" title="zan" src="img/iconfont-zanzan1.png"></div>
            </s:else>
          </s:if>
          <s:else>
            <div class="lds">${myArticles[9]}<img title="shoucang" class="agree" src="img/iconfont-icon.png"></div>
            <div class="lds">${myArticles[8]}<img class="agree" title="cai" src="img/iconfont-zanzan-copy.png"></div>
            <div class="lds">${myArticles[7]}<img class="agree" title="zan" src="img/iconfont-zanzan1.png"></div>
          </s:else>
        </div>
      </s:if>
      <s:else>
        <div class="singlePicArticle" id="${myArticles[0]}"><div class="title"><h4>${myArticles[1]}</h4></div><div class="content">${myArticles[2]}</div><div class="fct">${myArticles[4]}</div><div class="fct">　·　${myArticles[6]}条评论　·　</div><div class="fct">${myArticles[5].toString().substring(0,10)}</div>
          <div class="lds">${myArticles[9]}<img title="shoucang" class="agree" src="img/iconfont-icon.png"></div>
          <div class="lds">${myArticles[8]}<img class="agree" title="cai" src="img/iconfont-zanzan-copy.png"></div>
          <div class="lds">${myArticles[7]}<img class="agree" title="zan" src="img/iconfont-zanzan1.png"></div>
        </div>
      </s:else>
    </s:iterator>


    <%--文章页码--%>
    <nav>
      <ul class="pagination">
          <li ><a href="${session.className}.html" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
          <s:if test="#session.currentPage <= 3 || #session.pageCounts <= 7">
            <s:iterator value="{1,2,3,4,5,6,7}" status="status">
              <s:if test="#status.index < #session.pageCounts">
                <s:if test="#status.index+1 == #session.currentPage">
                  <li class="active"><a href="#"><s:property /> <span class="sr-only">(current)</span></a></li>
                </s:if>
                <s:else>
                  <li ><a href="${session.className}/page/<s:property />.html"><s:property /> </a></li>
                </s:else>
              </s:if>
            </s:iterator>
          </s:if>
          <s:elseif test="#session.currentPage <= #session.pageCounts - 3">
            <s:iterator value="{#session.currentPage-3,#session.currentPage-2,#session.currentPage-1,#session.currentPage,#session.currentPage+1,#session.currentPage+2,#session.currentPage+3}" var="pages">
              <s:if test="#pages == #session.currentPage">
                <li class="active"><a href="#"><s:property /> <span class="sr-only">(current)</span></a></li>
              </s:if>
              <s:else>
                <li ><a href="${session.className}/page/<s:property />.html"><s:property /> </a></li>
              </s:else>
            </s:iterator>
          </s:elseif>
          <s:elseif test="#session.currentPage <= #session.pageCounts">
            <s:iterator value="{#session.pageCounts-6,#session.pageCounts-5,#session.pageCounts-4,#session.pageCounts-3,#session.pageCounts-2,#session.pageCounts-1,#session.pageCounts}" var="pages">
              <s:if test="#pages == #session.currentPage">
                <li class="active"><a href="#"><s:property /> <span class="sr-only">(current)</span></a></li>
              </s:if>
              <s:else>
                <li ><a href="${session.className}/page/<s:property />.html"><s:property /> </a></li>
              </s:else>
            </s:iterator>
          </s:elseif>
          <li><a href="${session.className}/page/<s:property value="#session.pageCounts" />.html" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
      </ul>
    </nav>

  </div>

  <!-- 其他 -->
  <div class="otherExtend">
    <!-- 天气 -->
    <div class="weather">
      <div class="weatherCity" onselectstart='return false'>
        <label for="checkCity">
          <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
          <input id="checkCity" type="checkbox" /> 松江
        </label>
      </div>
      <div class="weatherWind">
        北风3级
      </div>
      <div class="weatherPM">
        重度333
      </div>

      <div class="choosCity">
        <div class="province">
          <label for="city0">
            <div id="city0Choose">
              <input id="city0" type="checkbox" />
              <div class="provinceName">上海</div>
              <div class="provinceIcon">
                <span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>
              </div>
            </div>
          </label>
          <div class="otherCitys" id="otherCitysLeft">
            <div class="otherCity">北京</div>
            <div class="otherCity">上海</div>
            <div class="otherCity">天津</div>
            <div class="otherCity">黑龙江</div>
            <div class="otherCity">黑龙江</div>
            <div class="otherCity">黑龙江</div>
            <div class="otherCity">黑龙江</div>

          </div>
        </div>
        <div class="province">
          <label for="city1">
            <div id="city1Choose">
              <input id="city1" type="checkbox" />
              <div class="provinceName">上海</div>
              <div class="provinceIcon">
                <span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>
              </div>
            </div>
          </label>
          <div class="otherCitys" id="otherCitysRight">
            <div class="otherCity">上海</div>
            <div class="otherCity">上海</div>
            <div class="otherCity">上海</div>
            <div class="otherCity">上海</div>
          </div>
        </div>

        <button type="button" id="changeCity" class="btn btn-warning">确定</button>
        <button type="button" id="cancleCity" class="btn btn-default">取消</button>
      </div>

      <div class="weatherDay" id="today">
        <div class="dayTitle">今天 </div>
        <div class="dayWeather"><img class="weatherIcon" src="./img/weather/iconfont-qingtian.png" /></div>
        <div class="dayTempera">10/19</div>
      </div>
      <div class="weatherDay">
        <div class="dayTitle">明天 </div>
        <div class="dayWeather"><img class="weatherIcon" src="./img/weather/iconfont-qingtian.png" /></div>
        <div class="dayTempera">10/19</div>
      </div>
      <div class="weatherDay">
        <div class="dayTitle">后天 </div>
        <div class="dayWeather"><img class="weatherIcon" src="./img/weather/iconfont-qingtian.png" /></div>
        <div class="dayTempera">10/19</div>
      </div>

    </div>

    <!-- 时事要闻 -->
    <div class="newest">
      <div class="newestTilte">
        <h3>实时要闻</h3>
      </div>
      <div class="newestLiDiv">
        <ul>
          <s:iterator value="#session.realTitles" var="titles">
            <li class="newstLi" id="news${titles[1]}">${titles[0]}</li>
          </s:iterator>
        </ul>
      </div>
    </div>

    <%--精彩评论--%>
    <div class="newest">
      <div class="newestTilte">
        <h3>精彩评论</h3>
      </div>
      <div class="bestComment">

      <s:iterator value="#session.realComments" var="realComments">
        <div class="singleComent">
          <div class="nameCount">
            <div class="commentHeadDiv">
              <img class="commentHead" src="./img/userphoto/head.jpg" />
            </div>
            <div class="commentName">
                ${realComments[2]}
            </div>
            <div class="praiseCount" title="jjjj" id="comment${realComments[5]}">
              <s:if test="#session.currentUser != null">
                <s:if test="#realComments[6] == 1">
                  <img class="commentAgree" title="yizan" src="img/iconfont-zanzan2.png" />
                </s:if>
                <s:else>
                  <img class="commentAgree" title="zan" src="img/iconfont-zanzan1.png" />
                </s:else>
              </s:if>
              <s:else>
                <img class="pleaseLogin" src="img/iconfont-zanzan1.png" />
              </s:else>


              <div class="realNumber">
                  ${realComments[4]}
              </div>
            </div>
          </div>
          <div class="comentConent">
              ${realComments[3]}
          </div>
          <div class="comentOf">
            评论于：
            <div class="comentArticleTitle" id="news${realComments[0]}">
                ${realComments[1]}
            </div>
          </div>
        </div>
      </s:iterator>

      </div>
    </div>

    <!-- copyright  -->
    <div class="copyRight">
      <p class="copyRightSingle">© 2016 文章系统 wenzhang.com</p>
      <p class="copyRightSingle">中国互联网举报中心</p>
      <p class="copyRightSingle">京ICP证140141号</p>
      <P class="copyRightSingle">京ICP备12025439号-3</P>
      <p class="copyRightSingle">网络文化经营许可证</p>
      <P class="copyRightSingle">跟帖评论自律管理承诺书</P>
      <P class="copyRightSingle">违法和不良信息举报：010-58341833</P>
      <P class="copyRightSingle">公司名称：北京字节跳动科技有限公司</P>
      <P class="copyRightSingle">北京字节跳动网络技术有限公司</P>
      <P class="copyRightSingle">京公网安备 11010802020116号</P>
    </div>
  </div>

</div>

<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="./js/index.js"></script>
</body>

</html>

