<#ftl strip_whitespace=true>
<#--
 * 门户层通用的模板都定义在此宏里面，供所有模板ftl文件使用。
 * 该模块会自动往所有模块文件里 import 进去。
 * ${contextPath} 为 应用上下文根性目录:如 /xxx
-->
<#global contextPath>${request.getContextPath()}</#global>
<#global staticResPath>${request.getContextPath()}</#global>
<#--
 * js缓存变量定义：yyyymmddhhmm
-->
<#global jsversion>${JSVERSION}</#global>
<#-- 往表单插入令牌 -->
<#macro csrf_token><input type="hidden" name="_al_ec_token" id="_al_ec_token" value="${Request['_al_ec_token']}"/></#macro>
<#--
* 令牌JS要放在JS头最顶处.
*-->
<#macro csrf_token_js>
	<script type="text/javascript">
	<#-- 设置全局contextPath	-->
	var contextPath = "${request.getContextPath()}";
	var globalAppDesc = "${APPDESC}";
	<#--
	  function getToken(){
		var token="${Request['_al_ec_token']}";
		return token;
	}-->
	</script>
</#macro>
<#-- 列表转字符串，并分隔 -->
<#function list2str4split pList key c>
	<#assign s=''>
	<#list pList as p>
		<#if s!=''><#assign s = s+c+p[key]><#else><#assign s = p[key]></#if>
	</#list>
	<#return s>
</#function>

<#-- 分页组件 -->
<#-- 分页组件simplePagination pageNo-当前页码  totalPages-总页数  pageBlockNum-显示页码个数  callBackFunc-回调函数执行再查询操作，包括一个入参：当着页码  -->
<#macro simplePagination pageNo totalPages pageBlockNum callBackFunc>
<#if (pageModel?? && pageBlockNum?? && callBackFunc??)>
<div class="paging" id="ec-pagination" callBack="${callBackFunc}">
	<#if (pageNo<=1)>
		<label><span class="pageUpGray">上一页</span></label>
	</#if>
	<#if (pageNo>1)>
		<label><span id="ec-page-prevs" class="pageUpOrange" page="${pageNo-1}">上一页</span></label>
	</#if>
	<#if (pageBlockNum>0)>
		<label>	
		<#assign j=((pageNo-1)/pageBlockNum)?int />
		<#if (totalPages<=pageBlockNum)>
			<#assign k = totalPages>
		<#else>
			<#if (( j * pageBlockNum+pageBlockNum)<= totalPages) >
				<#assign k = pageBlockNum>
			<#else>
				<#assign k = totalPages- j*pageBlockNum>
			</#if>	
		</#if>
		<#list 1..k as i>
			<#if (pageBlockNum*j+i)==pageNo>
				<a class="pagingSelect" href="javascript:void(0);">${pageBlockNum*j+i}</a>
			<#else>
				<a id="ec-page-${pageBlockNum*j+i}" class="fontBlueB" href="#loc-page" page="${pageBlockNum*j+i}">${pageBlockNum*j+i}</a>
			</#if>
		</#list>
		</label>
	</#if>
	<#if (pageNo>=totalPages)>
		<label><span class="nextPageGray">下一页</span></label>
	</#if>
	<#if (pageNo<totalPages)>
		<label><span id="ec-page-next" class="nextPageGrayOrange" page="${pageNo+1}">下一页</span></label>
	</#if>
	<label class="marginTop4" id="ec-total-page" page="${totalPages}">共 ${totalPages} 页</label>
	<label class="marginTop4">跳转至</label>	<input id="ec-input-spec" type="text" class="inputW20H20" /><label class="marginTop4">页</label>
	<a id="ec-btn-jump" href="#loc-page" class="determineBtn">跳转</a>
</div>
<#else>
	<iuput type="hidden" value="simplePagination error pageNo or totalPages or pageBlockNum or callBackFunc is undefined"/>
</#if>
</#macro>

<#-- 简化版的分页组件，无总条目数支持 -->
<#macro simplifyModelPagination pageModel callBackFunc>
	<#if (pageModel?? && callBackFunc??)>
	<div class="paging" id="ec-pagination" callBack="${callBackFunc}">
	<#if (pageModel.pageNo<=1)>
	<label><span class="pageUpGray">上一页</span></label>
	<#else>
	<label><span id="ec-page-prevs" class="pageUpOrange" page="${pageModel.pageNo-1}">上一页</span></label>
	</#if>	
	<label>
		<a class="pagingSelect" href="javascript:void(0);">${pageModel.pageNo}</a>
	</label>
	<#if (pageModel.list?size<pageModel.pageSize)>
	<label><span class="nextPageGray">下一页</span></label>
	<#else>
	<label><span id="ec-page-next" class="nextPageGrayOrange" page="${pageModel.pageNo+1}">下一页</span></label>
	</#if>
	<label class="marginTop4">跳转至</label>
		<input id="ec-input-spec" type="text" class="inputW20H20" />
	<label class="marginTop4">页</label>
	<a id="ec-btn-jump" href="#loc-page" class="determineBtn">跳转</a>
	</div>
	<#else>
	<iuput type="hidden" value="分页信息缺失"/>
	</#if>
</#macro>

<#-- 分页组件modelPagination pageModel-分页封装对象对应类：PageModel使用  pageBlockNum-显示页码个数  callBackFunc-回调函数执行再查询操作，包括一个入参：当着页码  -->
<#macro modelPagination pageModel pageBlockNum callBackFunc>
<#if (pageModel?? && pageBlockNum?? && callBackFunc??)>
<div class="paging" id="ec-pagination" callBack="${callBackFunc}">
	<#if (pageModel.pageNo<=1)>
		<label><span class="pageUpGray">上一页</span></label>
	</#if>
	<#if (pageModel.pageNo>1)>
		<label><span id="ec-page-prevs" class="pageUpOrange" page="${pageModel.pageNo-1}">上一页</span></label>
	</#if>
	<#if (pageBlockNum>0)>
		<label>	
		<#assign j=((pageModel.pageNo-1)/pageBlockNum)?int />
		<#if (pageModel.totalPages<=pageBlockNum)>
			<#assign k = pageModel.totalPages>
		<#else>
			<#if (( j * pageBlockNum+pageBlockNum)<= pageModel.totalPages) >
				<#assign k = pageBlockNum>
			<#else>
				<#assign k = pageModel.totalPages- j*pageBlockNum>
			</#if>	
		</#if>
		<#list 1..k as i>
			<#if (pageBlockNum*j+i)==pageModel.pageNo>
				<a class="pagingSelect" href="javascript:void(0);">${pageBlockNum*j+i}</a>
			<#else>
				<a id="ec-page-${pageBlockNum*j+i}" class="fontBlueB" href="#loc-page" page="${pageBlockNum*j+i}">${pageBlockNum*j+i}</a>
			</#if>
		</#list>
		</label>
	</#if>
	<#if (pageModel.pageNo>=pageModel.totalPages)>
		<label><span class="nextPageGray">下一页</span></label>
	</#if>
	<#if (pageModel.pageNo<pageModel.totalPages)>
		<label><span id="ec-page-next" class="nextPageGrayOrange" page="${pageModel.pageNo+1}">下一页</span></label>
	</#if>
	<label class="marginTop4" id="ec-total-page" page="${pageModel.totalPages}">共 ${pageModel.totalPages} 页</label>
	<label class="marginTop4">跳转至</label>	<input id="ec-input-spec" type="text" class="inputW20H20" /><label class="marginTop4">页</label>
	<a id="ec-btn-jump" href="#loc-page" class="determineBtn">跳转</a>
</div>
<#else>
	<iuput type="hidden" value="simplePagination error pageModel or pageBlockNum or callBackFunc is undefined"/>
</#if>
</#macro>
<#-- 当前位置导航功能 begin -->
<#macro NavBar curMenuNo>
<#if curMenuNo??>
	 <div id="ec-navbar" class="position" curMenuNo="${curMenuNo}"></div>  
</#if>
</#macro>
<#-- 当前位置导航功能  end -->
<#-- 
	滚动分页功能  nameSpace:命名空间  mode:刷新或者加载分页数据  totalPages:总页数  absTop:滚动块距离头部长度 absBottom:滚动块距离底部长度 
     callback:分页入口回调 (要求:入参json对象"page":页码,"scroll":用于刷新滚动对象)
   begin -->
<#macro scrollPagination nameSpace mode totalPages absTop absBottom callback>
<div id="${nameSpace}_wrapper" class="wrapper" style="top:${absTop}px;bottom:${absBottom}px;" callback="${callback}">
	<div id="${nameSpace}_scroller" class="scroller">
		<#if mode?? && (mode=="down" || mode="both")>
		<div id="pullDown" name="${nameSpace}_pull_down">
			<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
		</div>
		</#if>
		<#nested>
		<#if mode?? && (mode=="up" || mode="both")>
		<div id="pullUp" name="${nameSpace}_pull_up">
			<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>&nbsp;&nbsp;
			<font id="${nameSpace}_fnt_scroll_cur_page" class="green">1</font> / <font id="${nameSpace}_fnt_scroll_total_pages">${totalPages}</font>
		</div>
		</#if>
	</div>
</div>
<script type="text/javascript">$(function(){$.scrollInit('${nameSpace}');});</script>
</#macro>
<#macro scrollPaginationTab nameSpace mode totalPages absTop absBottom callback>
<tr id="${nameSpace}_wrapper" class="wrapper" style="top:${absTop}px;bottom:${absBottom}px;" callback="${callback}">
	<tr id="${nameSpace}_scroller" class="scroller">
		<#if mode?? && (mode=="down" || mode="both")>
		<tr id="pullDown" name="${nameSpace}_pull_down">
			<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
		</tr>
		</#if>
		<#nested>
		<#if mode?? && (mode=="up" || mode="both")>
		<tr id="pullUp" name="${nameSpace}_pull_up">
			<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>&nbsp;&nbsp;
			<font id="${nameSpace}_fnt_scroll_cur_page" class="green">1</font> / <font id="${nameSpace}_fnt_scroll_total_pages">${totalPages}</font>
		</tr>
		</#if>
	</rt>
</tr>
<script type="text/javascript">$(function(){$.scrollInit('${nameSpace}');});</script>
</#macro>
<#-- 滚动分页功能  begin-->  