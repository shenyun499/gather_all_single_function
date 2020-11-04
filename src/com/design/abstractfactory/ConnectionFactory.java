package com.design.abstractfactory;

/**
 * @Description
 * 抽象工厂
 * @Author
 * @Date 2019/6/218:45
 */
public class ConnectionFactory {
    public static void main(String[] args) {
        //选择使用mysql数据库进行对login表进行插入一条数据，对注册表查询一条数据
        Factory mysqlFactory = new MysqlFactory();
        mysqlFactory.createLogin().insert();
        mysqlFactory.createRegister().query();

        //选择使用oracle数据库进行对login表进行插入一条数据，对注册表查询一条数据
        Factory oracleFactory = new OracleFactory();
        oracleFactory.createLogin().insert();
        oracleFactory.createRegister().query();

    }

}

//登录表与注册表接口定义
interface Login {
    void insert();
    void query();
}
interface Register {
    void insert();
    void query();
}

//mysql实现登录表
class MysqlLogin implements Login {
    @Override
    public void insert() {
        System.out.println("使用mysql,在login表实现插入一条数据");
    }

    @Override
    public void query() {
        System.out.println("使用mysql，在login表查询一条数据");
    }
}
//mysql实现注册表
class MysqlRegister implements Register {
    @Override
    public void insert() {
        System.out.println("使用mysql,在Register实现插入一条数据");
    }

    @Override
    public void query() {
        System.out.println("使用mysql,在Register实现查询一条数据");
    }
}

//oracle实现登录表
class OracleLogin implements Login {
    @Override
    public void insert() {
        System.out.println("使用oracle,在login表实现插入一条数据");
    }

    @Override
    public void query() {
        System.out.println("使用oracle，在login表查询一条数据");
    }
}
//mysql实现注册表
class OracleRegister implements Register {
    @Override
    public void insert() {
        System.out.println("使用oracle,在Register实现插入一条数据");
    }

    @Override
    public void query() {
        System.out.println("使用oracle,在Register实现查询一条数据");
    }
}

//定义工厂
interface Factory {
    Login createLogin();
    Register createRegister();
}

//系列1mysql实现工厂类
class MysqlFactory implements Factory {
    @Override
    public Login createLogin() {
        return new MysqlLogin();
    }

    @Override
    public Register createRegister() {
        return new MysqlRegister();
    }
}
//系列2oracle实现工厂类
class OracleFactory implements Factory {
    @Override
    public Login createLogin() {
        return new OracleLogin();
    }

    @Override
    public Register createRegister() {
        return new OracleRegister();
    }
}