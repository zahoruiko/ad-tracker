<#macro campaignsList>
    <div><h3>My campaigns:</h3></div>
    <div>${message?ifExists}</div>
    <div>
        <table width="100%" name="campaignsList" class="table table-sm table-hover">
            <thead class="thead-light">
                <tr>
                    <th>Target</th>
                    <th>Target URL</th>
                    <th>Source</th>
                    <th>Medium</th>
                    <th>Archived</th>
                    <th>Visitors</th>
                    <th>Details</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>    
            </thead>
            <tbody>
                <#list page.content as campaign>
                    <tr>
                        <td>${campaign.destination}</td>
                        <td>${campaign.destinationUrl}</td>
                        <td>${campaign.source}</td>  
                        <td>${campaign.medium}</td>
                        <td>${campaign.archived?string('yes', 'no')}</td>
                        <td><a href="/campaignVisitors/${campaign.code}">Visitors</a></td>
                        <td><a href="/campaignDetails/${campaign.code}">Details</a></td>
                        <td><a href="/editCampaign/${campaign.code}">Edit</a></td>
                        <td><a href="/campaignDeleteConfirmation/${campaign.code}">Delete</a></td>
                    </tr>
                <#else>
                    <tr>
                        <td colspan="9">
                            <strong>No campaigns.</strong> <a href="/addNewCampaign">Add new campaign</a>
                        </td>
                    </tr>            
                </#list>                      
            </tbody>
        </table>
    </div>   
</#macro>


<#macro campaignDetailsForm>
    <div><h3>Campaigns:</h3></div>
    <div>${message?ifExists}</div>
    <div>
        <form method="POST" action="${actionUrl?ifExists}">
            <div class="form-group">
                <label for="destination">Target (*):</label>
                <input type="text" id="destination" name="destination" class="form-control ${(destinationError??)?string('is-invalid', '')}" value="<#if campaign.destination??>${campaign.destination}</#if>" placeholder="Destination" />
                <#if destinationError??>
                    <div class="invalid-feedback">
                        ${destinationError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="destinationUrl">Target URL (*):</label>
                <input type="text" id="destinationUrl" name="destinationUrl" class="form-control ${(destinationUrlError??)?string('is-invalid', '')}" value="<#if campaign.destinationUrl??>${campaign.destinationUrl}</#if>" placeholder="http://www.target-site.com/target-page.html" />
                <#if destinationUrlError??>
                    <div class="invalid-feedback">
                        ${destinationUrlError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="source">Traffic source (*):</label>
                <input type="text" id="source" name="source" class="form-control ${(sourceError??)?string('is-invalid', '')}" value="<#if campaign.source??>${campaign.source}</#if>" placeholder="Web-site, newsleter or ..." />
                <#if sourceError??>
                    <div class="invalid-feedback">
                        ${sourceError}
                    </div>
                </#if>                            
            </div>               
            <div class="form-group">
                <label for="medium">Traffic medium (*):</label>
                <input type="text" id="medium" name="medium" class="form-control ${(mediumError??)?string('is-invalid', '')}" value="<#if campaign.medium??>${campaign.medium}</#if>" placeholder="Search, banner, CPA, CPC, ..." />
                <#if mediumError??>
                    <div class="invalid-feedback">
                        ${mediumError}
                    </div>
                </#if>
            </div>            
            <div class="form-group">
                <label for="keywords">Keywords:</label>
                <input type="text" id="keywords" name="keywords" class="form-control ${(keywordsError??)?string('is-invalid', '')}" value="<#if campaign.keywords??>${campaign.keywords}</#if>" placeholder="Keywords" />
                <#if keywordsError??>
                    <div class="invalid-feedback">
                        ${keywordsError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="visitCost">Visit cost:</label>
                <input type="text" id="visitCost" name="visitCost" class="form-control ${(visitCostError??)?string('is-invalid', '')}" value="<#if campaign.visitCost??>${campaign.visitCost}<#else>0.00</#if>" placeholder="Visit cost" />
                <#if visitCostError??>
                    <div class="invalid-feedback">
                        ${visitCostError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="budget">Budget:</label>
                <input type="text" id="budget" name="budget" class="form-control ${(budgetError??)?string('is-invalid', '')}" value="<#if campaign.budget??>${campaign.budget}</#if>" placeholder="Budget" />
                <#if budgetError??>
                    <div class="invalid-feedback">
                        ${budgetError}
                    </div>
                </#if>
            </div>            
            <input type="hidden" name="code" value="<#if campaign.code??>${campaign.code}</#if>" />
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <small>* required</small><br />
            <button type="submit" class="btn btn-primary">Submit!</button>
        </form>
    </div>    
</#macro>


<#macro campaignDetails>
    <div><h3>Campaign:</h3></div>
    <div>${message?ifExists}</div>
    <div>
        <table name="campaignDetails" class="table table-sm table-hover">
            <tr><td>ID:</td>
                <td>${campaign.code}</td></tr>
            <tr><td>Input URL:</td>
                <td>${clickRegistrationPath}${campaign.code}</td></tr>
            <tr><td>Destination:</td>
                <td>${campaign.destination}</td></tr>
            <tr><td>Destination URL:</td>
                <td>${campaign.destinationUrl}</td></tr>
            <tr><td>Source:</td>
                <td>${campaign.source}</td></tr>
            <tr><td>Medium:</td>
                <td>${campaign.medium}</td></tr>
            <tr><td>Keywords:</td>
                <td>${campaign.keywords}</td></tr>
            <tr><td>Visit cost:</td>
                <td>${campaign.visitCost}</td></tr>
            <tr><td>Budget:</td>
                <td>${campaign.budget}</td></tr>
            <tr><td>Created:</td>
                <td>${campaign.created}</td></tr>
            <tr><td>Updated:</td>
                <td>${campaign.updated}</td></tr>
            <tr><td>Archived:</td>
                <td>${campaign.archived?string('yes', 'no')}</td></tr>
            <tr><td>Visitors:</td>
                <td><a href="/campaignVisitors/${campaign.code}">Visitors list</a></td></tr>
        </table>
        <form action="/editCampaign/${campaign.code}" method="GET">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit" class="btn btn-primary">Edit</button>
        </form>
    </div>
</#macro>