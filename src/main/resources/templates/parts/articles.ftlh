<#macro articlesList>
    <div><h3>Articles:</h3></div>
    <div>${message?ifExists}</div>
    <div>
        <table width="100%" name="articlesList" class="table table-sm table-hover">
            <thead class="thead-light">
                <tr>
                    <th>ID</th>
                    <th>Theme</th>
                    <th>Title</th>
                    <th>Created</th>
                    <th>Visible</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>    
            </thead>
            <tbody>
                <#list page.content as article>
                    <tr>
                        <td>${article.articleId}</td>
                        <td>${article.theme}</td>
                        <td><a href="/admin/articleDetails/${article.articleId}">${article.title}</a></td>
                        <td>${article.created}</td>
                        <td>${article.visible?string('yes', 'no')}</td>
                        <td><a href="/admin/editArticle/${article.articleId}">Edit</a></td>
                        <td><a href="/admin/articleDeleteConfirmation/${article.articleId}">Delete</a></td>
                    </tr>
                <#else>
                    <tr>
                        <td colspan="7">
                            <strong>No articles.</strong> <a href="/admin/addArticle">Add article</a>
                        </td>
                    </tr>            
                </#list>                      
            </tbody>
        </table>
    </div>   
</#macro>


<#macro articleDetailsForm>
    <div><h3>Article:</h3></div>
    <div>${message?ifExists}</div>
    <div>
        <form method="POST" action="${actionUrl?ifExists}">
            
            <div class="form-group">
                <label for="theme">Theme: (*)</label>
                <select class="custom-select" id="theme" name="theme">
                    <#if article.theme??>
                        <option>Choose a theme ...</option>
                        <option value="About" <#if article.theme == "About">selected</#if> >About</option>
                        <option value="Contacts" <#if article.theme == "Contacts">selected</#if> >Contacts</option>
                        <option value="F.A.Q." <#if article.theme == "F.A.Q.">selected</#if> >F.A.Q.</option>
                        <option value="Terms" <#if article.theme == "Terms">selected</#if> >Terms</option>                        
                    <#else>
                        <option selected>Choose a theme ...</option>
                        <option value="About">About</option>
                        <option value="Contacts">Contacts</option>
                        <option value="F.A.Q.">F.A.Q.</option>
                        <option value="Terms">Terms</option>
                    </#if>
                </select>
                <#if themeError??>
                    <div class="invalid-feedback">
                        ${themeError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="title">Title: (*)</label>
                <input type="text" id="title" name="title" class="form-control ${(destinationError??)?string('is-invalid', '')}" value="<#if article.title??>${article.title}</#if>" placeholder="Article title" />
                <#if titleError??>
                    <div class="invalid-feedback">
                        ${titleError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="body">Article text: (*)</label>
                <textarea id="body" name="body" class="form-control ${(bodyError??)?string('is-invalid', '')}" rows="5"><#if article.body??>${article.body}</#if></textarea>
                <#if bodyError??>
                    <div class="invalid-feedback">
                        ${bodyError}
                    </div>
                </#if>
            </div>        
            <input type="hidden" name="code" value="<#if article.articleId??>${article.articleId}</#if>" />
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <small>* required</small><br />
            <button type="submit" class="btn btn-primary">Submit!</button>
        </form>
    </div>    
</#macro>


<#macro articleDetails>
    <div><h3>Article:</h3></div>
    <div>${message?ifExists}</div>
    <div>
        <table name="articleDetails" class="table table-sm table-hover">
            <tr><td>ID:</td>
                <td>${article.articleId?ifExists}</td></tr>
            <tr><td>Theme:</td>
                <td>${article.theme?ifExists}</td></tr>
            <tr><td>Title:</td>
                <td>${article.title?ifExists}</td></tr>
            <tr><td>Article text:</td>
                <td>${article.body?ifExists}</td></tr>
            <tr><td>Created:</td>
                <td>${article.created}</td></tr>
            <tr><td>Visible:</td>
                <td>${article.visible?string('yes', 'no')}</td></tr>
        </table>
        <form action="/admin/editArticle/${article.articleId}" method="GET">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit" class="btn btn-primary">Edit</button>
        </form>
    </div>
</#macro>


<#macro articleOutput>
    <div><h3>${article.title?ifExists}:</h3></div>
    <p class="text-justify">
        ${article.body?ifExists}
    </p>
</#macro>