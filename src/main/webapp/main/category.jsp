<%@page contentType="text/html;charset=utf-8" isELIgnored="false"%>
<div class="book_l_border1" id="__FenLeiLiuLan">
	<div class="book_sort_tushu">
		<h2>
			分类浏览
		</h2>
		
		<!--1级分类开始-->
		<c:forEach items="${category}" var="first" varStatus="i">
        <div class="bg_old" onmouseover="this.className = 'bg_white';"
				onmouseout="this.className = 'bg_old';">
				<h3>
					[<a href='list.main?id=${first.key.id}&parent_id=${first.key.id}'>${first.key.name}</a>]
				</h3>
				<ul class="ul_left_list">
                    <c:forEach var="second" items="${first.value}">
                    <!--2级分类开始-->
                    <li>
                        <a href='list.main?id=${second.id}&parent_id=${first.key.id}'>${second.name}</a>
                    </li>
                    <!--2级分类结束-->
                    </c:forEach>
				</ul>

				<div class="empty_left">
				</div>
			</div>

			<div class="more2">
			</div>
			<!--1级分类结束-->
        </c:forEach>

        <div class="bg_old">
			<h3>
				&nbsp;
			</h3>
		</div>
	</div>
</div>
