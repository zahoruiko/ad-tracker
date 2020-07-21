<#macro userDetails>
<div><h3>User details:</h3></div>
    <div>${message?ifExists}</div>
    <table class="table  table-sm table-hover">
        <tr><td>Name:</td><td>${user.name}</td></tr>
        <tr><td>E-mail:</td><td>${user.email}</td></tr>
    </table>
    <form action="/updateUserDetails" method="GET">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Edit</button>
    </form>
</#macro>


<#macro userDetailsForm>
    <div><h3>User details:</h3></div>
    <div><font color='red'>${message?ifExists}</font></div>
    <form method="POST">
        <div class="form-group">
            <label for="name">Name:</label><br>
            <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')}" name="name" value="<#if user??>${user.name}</#if>" placeholder="User name" />
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="email">E-mail:</label><br>
            <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')}" name="email"  value="<#if user??>${user.email}</#if>" placeholder="User E-mail" />
            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="password">New password:</label><br>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" name="password"  value="" placeholder="New password" />
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
            <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')}" name="password2"  value="" placeholder="New password one more time" />
            <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
            <small id="passwordHelp" class="form-text text-muted">The reliable password should be at least 8 characters long and contain uppercase and lowercase letters, numbers, and special characters.</small>
            <label for="currentPassword">Current password:</label><br>
            <input type="password" class="form-control ${(currentpasswordError??)?string('is-invalid', '')}" name="currentPassword"  value="" placeholder="Current password" />
            <#if currentpasswordError??>
                <div class="invalid-feedback">
                    ${currentpasswordError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Submit!</button>
    </form>
</#macro>