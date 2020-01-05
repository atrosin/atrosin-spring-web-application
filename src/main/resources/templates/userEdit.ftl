<#import "parts/common.ftl" as c>

<@c.page>
User editor
<form action="/user" method="post">
    Username:
    <input type="text" name = "username" value="${user.username}">
    <div>
    Password:
    <input type="text" name = "password" value="${user.password}">
    </div>
    <#list roles as role>
    <div>
        <label><input type="checkbox"name = "${role}" ${user.roles?seq_contains(role)?string("checked","")}>${role}</label>
    </div>
    </#list>
    <input type="hidden" name = "userId" value="${user.id}">
    <input type="hidden" name = "_csrf" value="${_csrf.token}">
<button type="submit">Save</button>
</form>
</@c.page>
<a href="/main">Main</a>
