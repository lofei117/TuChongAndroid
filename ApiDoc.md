#图虫REST Api Doc

base host : http://tuchong.com
base path : /rest/
base url : http://tuchong.com/rest/

##登录接口

accounts/login
POST

####请求参数
account : 用户名
password : RSA加密 （或者原始字符串 需要验证码时必须加密）
captcha_id : 验证码的id
captcha_token : 验证码中的文字
remember : 是否记住密码

范例

```
account : jerrysher
password : 49fbc30905ca6b02d168fae339f68da7b13f295d15d29cf54da19065adead63f46833100c46cd187e9ccde6b52f97f5652632503167c750269cdc7e5148fdf784219ab774aae6036c80d8234e96f58ba8fb6f31b7e0d528c1c4bd23af31f201afe61f1ee1e51e233fb6d776d8e72502114774b9c4e8cfcb240a021749ee9b9da
captcha_id : uLlX8alm6IfnF2zBNJXr
captcha_token : 1066
remember : on
```

####返回结果
成功返回类似

```
{
    "message": "登录成功",
    "hint": "正在刷新页面，请稍候",
    "identity": "1100130",
    "result": "SUCCESS"
}
```

登录失败

```
{
    "result": "ERROR",
    "code": 11,
    "message": "请填写验证码"
}
```

```
{
    "result": "ERROR",
    "code": 4,
    "message": "用户名或密码错误"
}
```

验证码错误
```
{
    "result": "ERROR",
    "code": 12,
    "message": "验证码错误"
}
```

##获得登录验证码

captcha/image
POST

####请求参数
无

####返回结果

```
{
    "captchaId": "CQlRT2IvMLi4lBEdqp2k",
    "captchaBase64": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHgAAAAoCAMAAAACNM4XAAAAP1BMVEUAAABrCBfQbXyUMUBlAhFqBxa4VWSKJzb1kqGxTl2+W2qEITCqR1byj56lQlF+GyqzUF+8WWhkARD2k6LPbHtujUulAAAAAXRSTlMAQObYZgAAAiFJREFUeJy8V9tu8yAMxk3bi6rqj/L+z/hrk3bXZgyCOdsQaBpXy8ngzx8+wE7iSDnrP4D1EQ7GfYGidZcPI3uiUOA+PwrNykcZw6GBjXCJb47oYw8AplKAGHRB5McY8pyZpJCBTmeSMWwstnnOkUvYTYb86FW2IDctdbniVEzFdwjXNBgfdkv+ph2ovw76wcybeKh0xt61n2Zgsdq0Ex3CzkNgW06qsO+yejTB+LQKDeRuXmJglbobTIBMHZQ8MO8wWrg8Ne6POP2mKrXeFL66+z8hviObGveLsb1poe52MORO+S941+8yfDUtpsKYlzL4UVjsIzJ3BGIe1R5TbxtTzZfseASF3gWDyt1GuzqXnxT56Fee0lMYjfiSO5i44VxXSxAsKRvXhu123VPA6uaQ814WrpW6M1o1WPg3bwHC1b+Ye7Jf5vw2tLmJGeMjCcgnSugiuyHPhTbdiVrqWSTRM3QUhUsDgdiyzAsBPFtkNLxaQUuG+9WnWhnZFdeObfaUpQT+r38RskbAVmk+XPUvFZkSdrhD3QwF8u3ZJJTBjRuKlJLKsndwKU+wgkPXNjtVNKDHWF2Z54kNr22V4J+csqd4iV7d8MtThlTPnNg5IXp1LKokfVqSXuWLubNX9Z2lzi+zRIuI1jx3YD9gCFbNf/RiWsjZ7x/7OXBEprQ9yyYzk03kyhbYQ5Y9oPHmGV69J+5Oxm5sWdejsf0LAAD//xcibc5LJ+FdAAAAAElFTkSuQmCC",
    "result": "SUCCESS"
}
```

