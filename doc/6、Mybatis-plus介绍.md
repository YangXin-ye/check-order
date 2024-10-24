# Mybatis-plus介绍
### 1、mybatis-plus中Lamada表达式讲解
1、现在我们要查询用户名称姓张的用户，那么我们应该怎么查询？
```java
     public void selectByName(){
        //创建一个针对于User表的查询条件 select * from user
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        //根据user表中的name字段进行查询，where name like '%张%'
        lambdaQueryWrapper.like(User::getName,"张");
        //调用查询，select * from user where name like '%张%'
        List<User> list = this.list(lambdaQueryWrapper);
    }
```
2、我们要吧姓张的用户的密码都改成123456
```java
    public void upUser(){
        //创建一个针对于User表的更新条件，update user
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        //设置要更新的字段，set password = '123456'
        lambdaUpdateWrapper.set(User::getPassword,"123456");
        //添加要更新的条件，where name like '%张%'
        lambdaUpdateWrapper.like(User::getName,"张");
        //调用更新 update user set password = '123456' where name like '%张%'
        this.update(lambdaUpdateWrapper);
    }
```
3、使用分页查询用户
```java
    public Page<Users> listUserPage(Integer pageNum, Integer pageSize, ListUserPageReq listUserPageReq) {
        //声明要查询的表，Users表，构建针对 Users 实体类的查询条件，select * from users
        LambdaQueryWrapper<Users> lambdaQuery = Wrappers.<Users>lambdaQuery();
        //判断如果传了名称，就根据名称名称当作条件查询
        if(StringUtil.isNotEmpty(listUserPageReq.getName())){
            //根据user表中的name字段进行查询，where name like '%name%'
            lambdaQuery.like(Users::getName,listUserPageReq.getName());
        }
        //创建分页对象，并且吧页码和分页大小传进去
        Page<Users> page = new Page<>(pageNum, pageSize);
        //调用分页查询，吧分页对象和条件传进去，进行查询 select * from users where name like '%name%'
        Page<Users> resultPage = baseMapper.selectPage(page, lambdaQuery);
        return resultPage;
    }
```
4、查询所有未删除的用户
```java
public List<Users> getUserList(){
    //声明要查询的表，Users表，构建针对 Users 实体类的查询条件，select * from users
    LambdaQueryWrapper<Users> lambdaQuery = Wrappers.<Users>lambdaQuery();
    //根据users表中的delete字段进行查询，where delete = 0
    lambdaQuery.eq(Users::getDelete,0);
    //调用查询 select * from users where delete = 0
    return this.list(lambdaQuery);
}
```
5、我们删除所有姓张的人
```java
public void delete(){
        //创建一个针对于User表的删除条件，delete user
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加要删除的条件，where name like '%张%'
        userLambdaQueryWrapper.like(User::getName,"张");
        //执行删除，delete user where name like '%张%'
        remove(userLambdaQueryWrapper);
    }
```
