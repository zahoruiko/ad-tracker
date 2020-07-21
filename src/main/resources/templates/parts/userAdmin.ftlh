
<#macro usersList>
    <div>Users list:</div>
    <table width="100%" name="usersList" class="table table-sm table-hover">
        <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>E-mail</th>
                <th>Role</th>
                <th> </th>
            </tr>
        </thead>
        <tbody>
            <#list page.content as user>
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>
                        <#list user.userRoles as role>
                            ${role}<#sep>, 
                        </#list>
                    </td>
                    <td><a href="/admin/userRole/${user.id}">Edit</a></td>
                </tr>
            <#else>    
                <tr>
                    <td colspan="5"><b>No users</b></td>
                </tr>
            </#list>
        </tbody>
    </table>
</#macro>


<#macro userRoleEditForm>
    <div><h3>User ''${user.name}'' roles:</h3></div>
    <div>${message?ifExists}</div>
    <div class="mt-5 mb-5">
        <form action="/admin" method="POST">
            <#list roles as role>
            <label> <input type="checkbox" name="${role}" ${user.userRoles?seqContains(role)?string("checked", "")}>${role}</label><br />
            </#list>
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" name="_csrf" value="${_csrf.token}" /><br/>
            <button type="submit" class="btn btn-primary">Save</button>
        </form>
    </div>
</#macro>