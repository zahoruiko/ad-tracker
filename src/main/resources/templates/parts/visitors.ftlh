<#macro visitorsList>
    <div><h3>Visits:</h3></div>
    <div>${message?ifExists}</div>
    <table width="100%" name="visitorsList" class="table  table-sm table-hover">
        <tr><th>Time</th><th>IP</th><th>Referer</th><th>User agent</th></tr>
        <#list page.content as visitor>
            <tr><td>${visitor.timestamp}</td><td>${visitor.userIp}</td><td>${visitor.referrerUrl}</td><td>${visitor.userAgent}</td></tr>
        <#else>
            <tr><td colspan="4"><strong>No data</strong></td></tr>
        </#list>
    </table>
</#macro>