<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 顶部工具条 -->
	<div class="top">
		<div class="topbar">
			<span class="welcome" style="float:left"><a
				href="javascript:void(0);"
				onclick="SetHome(this,'http://115.28.68.131')">设为首页</a> <a
				href="javascript:void(0);"
				onclick="AddFavorite('yershop商城',location.href)" title="yershop商城">收藏本站</a>
			</span> <span class="welcome" style="float:left"> <a
				href="javascript:void(0);"
				onclick="AddFavorite('yershop商城',location.href)" title="yershop商城"></a>
			</span>
			<div class="topaccount">
				<span class="operate_nav"> <span id="userfavor"><a
						rel="nofollow"><i></i>我的收藏&nbsp;<b></b></a> </span>
					<ul id="favormenu" class="top_lg_info_down" style="display:none;">
						<li><a rel="nofollow" href="${pageContext.request.contextPath}/CollectServlet?type=myCollect">收藏的商品</a></li>
					</ul>
				</span> <span class="operate_nav"> <span id="account"><a
						rel="nofollow">我的账号&nbsp;</a><i id="icount"
						class="fa fa-angle-down"></i> </span>
					<ul id="dbox" class="top_lg_info_down" style="display:none;">
						<li><a rel="nofollow" href="userMain.page">个人中心</a></li>
						<li><a rel="nofollow" href="changePassword.page">修改密码</a></li>
					</ul> |
				</span> <span class="operate_nav"> <span id="sell"><a
						rel="nofollow">我的订单&nbsp;<b></b></a> </span>
					<ul id="sellmenu" class="top_lg_info_down" style="display:none;">
						<li><a rel="nofollow" href="userMain.page?type=1">所有订单</a></li>
						<li><a rel="nofollow" href="userMain.page?type=2">待支付订单</a></li>
						<li><a rel="nofollow" href="userMain.page?type=3">待发货订单</a></li>
						<li><a rel="nofollow" href="userMain.page?type=4">待确认订单</a></li>
					</ul> |
				</span>
				<c:if test="${username!=null }">
					<span class="operate_nav"> 欢迎光临宅商城 <a href="" class="red">${username}</a>,<a
						rel="nofollow" href="${pageContext.request.contextPath}/UserServlet?type=mainOut">退出</a> |
					</span>
				</c:if>
				<c:if test="${username==null }">
					<span class="operate_nav"> 欢迎光临宅商城 ,请<a href="login.jsp">[登录]</a>&nbsp;<a
						href="rege.jsp" style="padding-left:0;padding-right:10px">
							[免费注册] </a> |
					</span>
				</c:if>
				<span class="operate_nav"> </span>
			</div>
		</div>
	</div>
	<script type="text/javascript">
//头部topbar会员中心显示与隐藏
var Account= document.getElementById('account');
            var Downmenu= document.getElementById('dbox');
            var timer = null;//定义定时器变量
            //鼠标移入div1或div2都把定时器关闭了，不让他消失
            Account.onmouseover = Downmenu.onmouseover = function ()
            {
				 //改变箭头方向
				$("i#icount").attr("class","fa fa-angle-up");
               
				 //改变背景颜色
				 Account.style.backgroundColor = '#fff';
				 //显示下拉框
                $("#dbox").show();
				//关闭定时执行
                clearTimeout(timer);
            }
			
            //鼠标移出div1或div2都重新开定时器，让他延时消失
            Account.onmouseout = Downmenu.onmouseout = function ()
            {
				$("i#icount").attr("class","fa fa-angle-down");
				Account.style.backgroundColor = '#F5F5F5';
				 //开定时器，每隔200微妙下拉框消失
                timer = setTimeout(function () { 
                    $("#dbox").hide(); }, 200);
            }
      	//头部topbar会员收藏显示与隐藏
userfavor;favormenu;time;
            var userfavor= document.getElementById('userfavor');
            var favormenu= document.getElementById('favormenu');
            var time = null;//定义定时器变量
            //鼠标移入div1或div2都把定时器关闭了，不让他消失
            userfavor.onmouseover = favormenu.onmouseover = function ()
            {
				 //改变箭头方向
			              
				 //改变背景颜色
				 userfavor.style.backgroundColor = '#fff';
				 //显示下拉框
                $("#favormenu").show();
				//关闭定时执行
                clearTimeout(time);
            }
			
            //鼠标移出div1或div2都重新开定时器，让他延时消失
            userfavor.onmouseout = favormenu.onmouseout = function ()
            {	
				userfavor.style.backgroundColor = '#F5F5F5';
				 //开定时器，每隔200微妙下拉框消失
                time = setTimeout(function () { 
                    $("#favormenu").hide(); }, 10);
            } 
	 //卖家中心显隐
usersell;sellmenu;clock;
            var usersell= document.getElementById('sell');
            var sellmenu= document.getElementById('sellmenu');
            var clock = null;//定义定时器变量
            //鼠标移入div1或div2都把定时器关闭了，不让他消失
            usersell.onmouseover = sellmenu.onmouseover = function ()
            {
				 //改变箭头方向
			              
				 //改变背景颜色
				 usersell.style.backgroundColor = '#fff';
				 //显示下拉框
                $("#sellmenu").show();
				//关闭定时执行
                clearTimeout(clock);
            }
			
            //鼠标移出div1或div2都重新开定时器，让他延时消失
            usersell.onmouseout = sellmenu.onmouseout = function ()
            {	
				usersell.style.backgroundColor = '#F5F5F5';
				 //开定时器，每隔200微妙下拉框消失
                clock = setTimeout(function () { 
                    $("#sellmenu").hide(); }, 10);
            } 		
</script>