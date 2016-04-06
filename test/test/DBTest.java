package test;

import domain.*;
import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Test;
import service.Weather;
import util.HibernateUtil;

import java.util.*;

/**
 * Created by sangzhenya on 2016/3/26.
 */
public class DBTest {
    @Test
    public void test() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(serviceRegistry).buildMetadata();
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.create(EnumSet.of(TargetType.DATABASE), metadata);
    }




    @Test
    public void testGet() {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            session.get(CommentLike.class, 1);

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Test
    public void testList() {
        List<List<String>> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        List<String> list2 = new ArrayList<>();
        list2.add("4");
        list2.add("5");
        list2.add("6");

        list.add(list1);
        list.add(list2);

        for (int i = 0; i < list1.size(); i++) {
            System.out.println("list1:" + list.get(0).get(i));
            System.out.println("list2:" + list.get(1).get(i));
        }
    }

    @Test
    public void testGetArticle() {
        Session session = null;
        Transaction tx = null;
        MyArticle article = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            article = (MyArticle) session.createQuery("from MyArticle where article_id = :id")
                    .setInteger("id", 2)
                    .uniqueResult();
            System.out.println(article.getTitle());
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Test
    public void findBestComment() {
        Session session = null;
        Transaction tx = null;
        List<Object[]> atrticleTitles = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            atrticleTitles = session.createQuery("select a.article_id, a.title, u.name,c.content, count(cl.myComment.comment_id)" +
                    "from MyArticle a, MyComment c, CommentLike cl, MyUser u " +
                    "where a.article_id = c.myArticle.article_id and c.comment_id = cl.myComment.comment_id and cl.myUser.user_id = u.user_id " +
                    "group by a.article_id,a.title,u.name,c.content, cl.myComment.comment_id order by count(c.comment_id) desc")
                    .setMaxResults(3)
                    .list();

            System.out.println(atrticleTitles.size() + "----------------");

            for (Object[] obj : atrticleTitles) {
                System.out.println(obj[0]);
                System.out.println(obj[1]);
                System.out.println(obj[2]);
                System.out.println(obj[3]);
                System.out.println(obj[4]);
                System.out.println("===================================");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Test
    public void getWeather() {
        String httpUrl = "http://apis.baidu.com/apistore/weatherservice/recentweathers";
        String httpArg = "cityname=%e6%9d%be%e6%b1%9f&cityid=101020900";
        String jsonResult = Weather.request(httpUrl, httpArg);
        String real = Weather.decodeUnicode(jsonResult);
        System.out.println(real);

    }

    @Test
    public void getUserClasses(){
        Session session = null;
        Transaction tx = null;
        List<MyClassification> myClassifications = null;

        List<Object[]> classTitleDesc = null;

        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            MyUser myUser = session.get(MyUser.class, 1);
            classTitleDesc = session.createQuery("select c.classification_name, c.classification_desc from MyUser u, MyClassification c, MySubscribe s " +
                    "where c.classification_id = s.myClassification.classification_id " +
                    "and u.user_id = s.myUser.user_id " +
                    "and u.user_id = :id")
                    .setInteger("id",myUser.getUser_id())
                    .list();

            myClassifications = session.createQuery("from MyClassification ").list();
            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
//        System.out.println(classTitleDesc.size()+"--------------------");
        for (MyClassification myClassification:myClassifications){
            boolean isFind = false;
            for (Object[] objects:classTitleDesc){
                if (myClassification.getClassification_name().equals(objects[0])){
                    isFind = true;
                    break;
                }
            }
            if (!isFind){
                Object[] objects = new Object[2];
                objects[0] = myClassification.getClassification_name();
                objects[1] = myClassification.getClassification_desc();
                classTitleDesc.add(objects);
            }
        }

        for (Object[] objects:classTitleDesc ){
            System.out.println("--------------------");
            System.out.println(objects[0]);
            System.out.println(objects[1]);
        }
    }

    @Test
    public void configDB() {
        configClassifi();
        save2myuser();
        for (int i = 0; i < 20; i++ ){
            int chooseArticle = (int)(1+Math.random()*4);

            switch (chooseArticle){
                case 1:
                    save2myarticle1();
                    break;
                case 2:
                    save2myarticle2();
                    break;
                case 3:
                    save2myarticle3();
                    break;
                case 4:
                    save2myarticle4();
                    break;
            }
        }
        save2mycomment();
        save2commentlike2();
        save2articlelike();
        save2articledislike();
        save2mysubscribe();
        save2mycollo();

        save2emdia0();
        save2emdia1();
        save2emdia2();
        save2emdia3();
        save2emdia4();
    }

    private void configClassifi() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            MyClassification myClassification0 = new MyClassification("热点", "这里是热点板块");
            MyClassification myClassification1 = new MyClassification("社会", "这里是社会板块");
            MyClassification myClassification2 = new MyClassification("娱乐", "这里是娱乐板块");
            MyClassification myClassification3 = new MyClassification("科技", "这里是科技板块");
            MyClassification myClassification4 = new MyClassification("体育", "这里是体育板块");
            MyClassification myClassification5 = new MyClassification("财经", "这里是财经板块");
            MyClassification myClassification6 = new MyClassification("军事", "这里是军事板块");
            MyClassification myClassification7 = new MyClassification("国际", "这里是国际板块");
            MyClassification myClassification8 = new MyClassification("时尚", "这里是时尚板块");
            MyClassification myClassification9 = new MyClassification("探索", "这里是探索板块");
            MyClassification myClassification10 = new MyClassification("美文", "这里是美文板块");
            MyClassification myClassification11 = new MyClassification("历史", "这里是历史板块");
            MyClassification myClassification12 = new MyClassification("故事", "这里是故事板块");
            MyClassification myClassification13 = new MyClassification("游戏", "这里是游戏板块");

            session.save(myClassification0);
            session.save(myClassification1);
            session.save(myClassification2);
            session.save(myClassification3);
            session.save(myClassification4);
            session.save(myClassification5);
            session.save(myClassification6);
            session.save(myClassification7);
            session.save(myClassification8);
            session.save(myClassification9);
            session.save(myClassification10);
            session.save(myClassification11);
            session.save(myClassification12);
            session.save(myClassification13);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    private void save2myuser() {
        for (int i = 1; i <= 100; i++) {
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                MyUser myUser = new MyUser("xinyue" + i, "xinyue");
                session.save(myUser);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    private void save2myarticle1() {


        for (int i = 1; i <= 53; i++) {
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();

//                date.toString().substring(0,10);
                MyArticle myArticle = new MyArticle();
                myArticle.setTitle(i + "省部级官员近期密集调整的最重要看点是啥？");
                myArticle.setAbstracts("全国两会后，中央在人事层面继续破立并举，尤其在立的层面，包括党政一把手在内的不少地方省部级领导干部履新。");
                myArticle.setArticle_pic("http://p2.pstatp.com/large/3e40004f8e5cb36c685");
                myArticle.setAuthor("新京报政事儿 ");
                myArticle.setContent(" " +
                        "<div><p>全国两会后，中央在人事层面继续破立并举，尤其在立的层面，包括党政一把手在内的不少地方省部级领导干部履新。</p><p>“政事儿”（微信ID：gcxxjgzh）注意到，在近期省部级人事调整中，涉及到的地方有：云南、广东、宁夏、新疆、河南、辽宁、甘肃、陕西、黑龙江和江苏等地。</p><p>截至3月28日，在省部级层面，至少有14人的职位发生了变化。</p><p>其中，<strong>宁夏自治区政府副主席刘可为和云南省委常委、昆明市委书记程连元等从正厅级晋升为副省级；河南省委副书记、省长候选人陈润儿，甘肃省委副书记林铎，陕西省委副书记、省政府党组书记胡和平等或将由副省级晋升为正部级。</strong></p><p>“政事儿”注意到，在近期地方省部级干部集中调整中，有以下几个看点。</p><p><strong>学者型官员受重用</strong></p><p>近期的人事调整中，两省级党委一把手的变动备受关注。</p><p><img src=\"http://p2.pstatp.com/large/3e40004f8e5cb36c685\" img_width=\"640\" img_height=\"393\" alt=\"省部级官员近期密集调整的最重要看点是啥？\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>谢伏瞻</p><p>一位是河南省长谢伏瞻，他接任河南省委书记，其前任郭庚茂已年满65岁；另外一位是陕西省长娄勤俭，他接任陕西省委书记，其前任赵正永也年满65岁。</p><p>除了两人都履新省级党委一把手外，谢伏瞻和娄勤俭至少还有三重共同点。</p><p>其一，<strong>两人都曾经有国务院单位任职经历。</strong></p><p>谢伏瞻曾长期在国务院智囊机构国务院发展研究中心和国务院研究室任职，先后任国研中心副主任，国研室主任。2013年3月，在国研室主任任上“空降”河南任省长。</p><p>娄勤俭曾任工信部副部长，2010年“空降”陕西，先后任副省长、省长。</p><p>其二，<strong>两人为同门师兄弟，都曾在华中科技大学（前身为华中工学院）攻读计算机相关专业。</strong>出生于1956年的娄勤俭是出生于1954年的谢伏瞻师弟。</p><p><strong>其三，两人均为“实打实”的学者型官员，都享受国务院政府特殊津贴。</strong></p><p>谢伏瞻曾获国家科技进步二等奖、两次获得孙冶方经济科学奖。孙冶方经济科学奖是中国经济学界的最高奖。</p><p>娄勤俭曾任攀钢连铸过程控制系统总设计师、国家863计划自动化领域主题专家、载人航天工程测控计算机系统总指挥、国务院信息化工作领导小组专家组成员、总装备部科技委计算机及软件技术专业组组长等。</p><p><img src=\"http://p1.pstatp.com/large/3e800038f0318762f65\" img_width=\"593\" img_height=\"331\" alt=\"省部级官员近期密集调整的最重要看点是啥？\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>今年全国两会期间，“政事儿”（微信ID：gcxxjgzh）曾在人民大会堂多次遇到谢伏瞻在会议结束后独自一人离开。3月5日，在接受“政事儿”采访时，谢伏瞻用四个字评价了总理政府工作报告：催人奋进。</p><p>近日，陕西省委专职副书记胡和平，接替娄勤俭任陕西省政府党组书记，按照人事惯例，胡和平也将会接任省长职务。</p><p>与谢伏瞻和娄勤俭一样，胡和平也是一位名副其实的学者型官员。曾有东京大学留学经历，并获工学博士学位。后历任清华水利水电工程系副主任，土木水利学院党委书记，清华大学党委组织部长、教务处长，清华大学副校长，清华大学党委书记等职。</p><p>2013年11月，胡和平“空降”浙江任省委常委、组织部长，2015年4月转任陕西省委专职副书记至今次调整。</p><p>值得一提的是，与胡和平当时在清华大学的搭档，清华大学原校长陈吉宁现任环保部部长，而胡和平的前任清华大学党委书记陈希，现任中组部常务副部长（正部级）。</p><p><strong>两新省委书记履新时的共同表态</strong></p><p>3月26日和3月27日，分别召开的河南和陕西全省领导干部会议上，中组部副部长姜信治出席会议并宣布中央有关两省委书记调整的决定。</p><p><img src=\"http://p3.pstatp.com/large/3250007cde15f724db8\" img_width=\"400\" img_height=\"271\" alt=\"省部级官员近期密集调整的最重要看点是啥？\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>河南全省领导干部会议</p><p>“政事儿”（微信ID：gcxxjgzh）注意到，在“交接班仪式”上，新任河南省委书记谢伏瞻和新任陕西省委书记娄勤俭在讲话时，都谈到了“核心意识”，<strong>两人一致表态，要坚决维护习近平总书记这个核心。</strong></p><p>3月26日，河南省委召开全省领导干部会议。</p><p>谢伏瞻强调，要坚定政治方向、服从服务大局，坚决贯彻落实中央各项决策部署。坚定不移向党中央看齐，向习近平总书记看齐，向党的理论和路线方针政策看齐，向党中央的各项决策部署看齐，<strong>坚决维护党中央权威，坚决维护习近平总书记这个核心，坚决服从党中央集中统一领导，跟着党中央的令旗走</strong>，找准中央精神与河南实际的结合点，确保中央决策部署在河南政令畅通、落地生根。要坚持党要管党、从严治党，着力提升各级党组织的政治保证能力。</p><p>3月27日，陕西省召开全省领导干部会议。</p><p>赵正永首先这样评价娄勤俭：娄勤俭政治坚定、对党忠诚、为人正派、学习勤奋、工作勤勉，作风扎实、待人谦和，自我要求严格，既有丰富的中央国家机关工作经历，又有一定时间的地方工作历练，大局意识和驾驭全局的能力强。</p><p><img src=\"http://p3.pstatp.com/large/3e600037f88fc1c20a5\" img_width=\"550\" img_height=\"320\" alt=\"省部级官员近期密集调整的最重要看点是啥？\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>陕西全省领导干部会议</p><p>新任省委书记娄勤俭在讲话中强调，要始终坚持正确政治方向，牢固树立政治意识、大局意识、核心意识、看齐意识，坚定不移向党中央看齐，<strong>自觉维护习近平总书记这个核心，始终在思想上政治上行动上与党中央保持高度一致。</strong></p><p><strong>有“京官”经历的两官员晋升</strong></p><p>“政事儿”（微信ID：gcxxjgzh）注意到，在本轮人事调整中，有两人曾经在北京市为官。</p><p><strong>现任云南省委常委、昆明市委书记程连元曾任北京市朝阳区委书记</strong>，2015年，程连元与时任北京市西城区委书记王宁一起被评为全国优秀县委书记，王宁在2015年10月出任北京市副市长。</p><p>此次，程连元由昆明市委书记晋升为云南省委常委后，两位北京“全国优秀县委书记”先后双升副部级。</p><p>程连元1961年出生，北京人，管理学博士。他从工厂最基层的技术员起步，一路在国企成长，后在企业“一把手”任上“由商而仕”，转任北京市工业促进局局长，后到朝阳区任职，9年间，先后任朝阳区区长、区委书记。</p><p><img src=\"http://p3.pstatp.com/large/2b60021161d79a78420\" img_width=\"447\" img_height=\"300\" alt=\"省部级官员近期密集调整的最重要看点是啥？\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>2015年7月，程连元在朝阳区委书记任上平调至昆明市委书记，近8个月后，晋升为省委常委，入列副省级干部序列。按惯例，省会城市党委一把手一般由省委常委兼任。在程连元之前，先后三任昆明市委书记仇和、张田欣和高劲松落马，其中，任职不满一年的高劲松并未进入省委常委会。</p><p>3月28日，《甘肃日报》报道：日前，中央决定，林铎任甘肃省委委员、常委、副书记，刘伟平同志不再担任甘肃省委副书记、常委、委员职务，另有任用。</p><p>3月28日，甘肃省委全面推进依法治省工作领导小组召开第一次会议，省委书记王三运出席会议并讲话，<strong>省领导林铎</strong>、冯健身、欧阳坚等出席会议。</p><p>上述报道显示，<strong>林铎排名在现任甘肃省政协主席冯健身（正部级）之前，排名再次之的欧阳坚为现任甘肃省委专职副书记。</strong></p><p>再综合现任甘肃省长刘伟平另有任用的消息，如无意外，林铎未来将出任甘肃省长，由副省级晋升为正部级。</p><p>林铎与前述程连元一样，此前也有长期的“京官”经历。<strong>他曾任北京市西城区委书记，是上述王宁的前任。</strong>2010年，林铎转任黑龙江省哈尔滨市长，晋升为副省级，两年后任市委书记并进入省委常委会。2014年8月，林铎转赴辽宁任省委常委、纪委书记。至近日，调任甘肃省委副书记。如未来出任甘肃省长，<strong>林铎从副部级到正部级用了6年时间。</strong></p><p><img src=\"http://p1.pstatp.com/large/3e800038f04c6200754\" img_width=\"550\" img_height=\"488\" alt=\"省部级官员近期密集调整的最重要看点是啥？\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>“政事儿”（微信ID：gcxxjgzh）注意到，与林铎和程连元一样，在现任的各地方官员中，也有不少有“京官”经历者。</p><p>除了现任中央政治局委员、重庆市委书记孙政才，江西省委书记强卫，黑龙江省长陆昊外，还有贵州省委常委、贵阳市委书记陈刚（1965年出生），西藏自治区党委副书记、自治区常务副主席、区党委政法委书记邓小刚（1967年出生），新疆自治区哈密地委书记刘剑（1970年出生，十八届中央委员会中唯一的70后候补委员），山西大同市委书记张吉福等。</p><p><strong>广东省委办公厅副秘书长外调宁夏，升副部</strong></p><p>3月初，中央批准邹铭任广东省委常委。全国两会后，广东省委决定，邹铭任省委秘书长、办公厅主任。中央和省委批准林木声不再担任省委常委和省委秘书长职务。</p><p>邹铭曾长期在民政部任职，赴广东前，任职民政部副部长。</p><p>3月24日上午，宁夏回族自治区十一届人大常委会第二十三次会议，决定任命刘可为为自治区人民政府副主席。</p><p>刘可为此前任广东省委副秘书长、办公厅主任。此次外调宁夏后由正厅级晋升为副部级。</p><p>“政事儿”（微信ID：gcxxjgzh）注意到，出生于1964年的刘可为，与中央政治局委员、广东省委书记胡春华先后在团中央、河北省政府、广东省委有四次工作上的交集。</p><p><img src=\"http://p1.pstatp.com/large/3e50004f33fa3719e76\" img_width=\"500\" img_height=\"327\" alt=\"省部级官员近期密集调整的最重要看点是啥？\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>胡春华任团中央书记处第一书记时，刘可为任团中央宣传部部长等职；胡春华任河北省长时，刘可为任省长助理、省政府研究室主任；胡春华2012年12月任广东省委书记，之后，刘可为调任广东省委副秘书长，2014年兼任省委办公厅主任。</p><p>“政事儿”发现，在此前的人事调整案例中，省委秘书长一般都是从本省干部中提拔，大多是此前的省委副秘书长、组工和宣传系统干部等。譬如，现任天津市委常委、市委秘书长成其圣早前曾任天津市委副秘书长、办公厅主任；现任贵州省委常委、省委秘书长刘奇凡早前也曾任贵州省委副秘书长。</p><p>而现任山西省委常委、秘书长王伟中则是在山西发生塌方式腐败后，由科技部副部长任上“空降”至山西。</p><p>在近期省部级人事调整中值得关注的还有，陈润儿与林铎两位的跨省调动。陈润儿现任河南省委副书记、省长候选人，此前任黑龙江省委专职副书记，林铎在调任甘肃省委副书记之前，任辽宁省委常委、省纪委书记。</p><p>另外，年龄也值得关注，出生于1956年3月的林铎已年满60岁。按惯例，副部级干部要在满60岁时退出一线领导岗位，而此次林铎跨地域调任甘肃，如接任省长，升任正部级后还将有五年在一线领导岗位上的工作时间。</p><p><strong>“政事儿”（微信ID:gcxxjgzh）撰稿：新京报记者 马俊茂 实习生 王俊</strong></p></div>" +
                        "");
                Calendar calendar = Calendar.getInstance();
                calendar.set(2016, 1, 4);
                Date date = calendar.getTime();
                myArticle.setPub_date(date);
                int userIndex = (int) (1 + Math.random() * 14);
                myArticle.setMyClassification(session.get(MyClassification.class, userIndex));
                session.save(myArticle);
//                System.out.println(date);
                tx.commit();

            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    private void save2myarticle2() {
        for (int i = 1; i <= 53; i++) {
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                Calendar calendar = Calendar.getInstance();
                calendar.set(2016, 3, 4);
                Date date = calendar.getTime();
//                date.toString().substring(0,10);
                MyArticle myArticle = new MyArticle();
                myArticle.setTitle(i + "高速公路上行驶一辆价值千万的兰博基尼，数秒后让人惊讶一幕出现");
                myArticle.setAbstracts("每当提起超级跑车，相信大部分人第一时间就会想到兰博基尼。在兰博基尼众多款车子中兰博基尼Aventador是最受到富豪们青睐的一款，这款车子曾在2011年的日内瓦车展");
                myArticle.setArticle_pic("http://p3.pstatp.com/large/25300089d427a7d0b75");
                myArticle.setAuthor("汽车新画报  ");
                myArticle.setContent(" " +
                        " <p>\n" +
                        "\n" +
                        "<p><img src=\"http://p3.pstatp.com/large/25300089d427a7d0b75\" img_width=\"640\" img_height=\"390\" alt=\"高速公路上行驶一辆价值千万的兰博基尼，数秒后让人惊讶一幕出现\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>每当提起超级跑车，相信大部分人第一时间就会想到兰博基尼。在兰博基尼众多款车子中兰博基尼Aventador是最受到富豪们青睐的一款，这款车子曾在2011年的日内瓦车展上首次亮相。然而兰博基尼虽好但是却也会发生一些不愉快的事故，就像英国的这位车主他将兰博基尼开到高速路时悲剧的事情发生了。</p>\n" +
                        "\n" +
                        "<p><img src=\"http://p1.pstatp.com/large/2cc00010c92e57c4d17\" img_width=\"640\" img_height=\"454\" alt=\"高速公路上行驶一辆价值千万的兰博基尼，数秒后让人惊讶一幕出现\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>图中为这辆兰博基尼Aventador，当时这位车主正开着他新买的兰博基尼Aventador前往海边，结果当他开到高速路上时，这辆兰博基尼Aventador的引起竟然起火了，当他从车上下来后数秒之间车子就已经燃起了大火。</p>\n" +
                        "\n" +
                        "<p><img src=\"http://p1.pstatp.com/large/25400089f5eca63019c\" img_width=\"640\" img_height=\"856\" alt=\"高速公路上行驶一辆价值千万的兰博基尼，数秒后让人惊讶一幕出现\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>图中为消防人员正在灭火，最后车主发现火势难以扑灭只好请求当地消防员的帮助，最后经过半小时的救援才将火势完全扑灭。</p>\n" +
                        "\n" +
                        "<p><img src=\"http://p2.pstatp.com/large/25400089f5f24d23493\" img_width=\"640\" img_height=\"457\" alt=\"高速公路上行驶一辆价值千万的兰博基尼，数秒后让人惊讶一幕出现\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>图中为车主站在兰博基尼Aventador的旁边，虽然车子的火焰被扑灭了，但是由于火势太大车子早已经被烧成了废铁。兰博基尼Aventador在国内的价格差不多在880万到1000万左右，不过对于这位富豪车主来说只是皮毛而已。</p>\n" +
                        "\n" +
                        "<p><img src=\"http://p1.pstatp.com/large/25400089f608a38f5ef\" img_width=\"576\" img_height=\"733\" alt=\"高速公路上行驶一辆价值千万的兰博基尼，数秒后让人惊讶一幕出现\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p>图中为这辆起火的兰博基尼Aventador，可以看见起火的引擎部分已经变成了废铁，车轮也被烧得只剩下了车轮毂。虽然兰博基尼起火已经不是第一次见了，但是每次看见都不由得让人觉得可惜。</p>\n" +
                        "</p>" +
                        "");
                myArticle.setPub_date(date);
                int userIndex = (int) (1 + Math.random() * 14);
                myArticle.setMyClassification(session.get(MyClassification.class, userIndex));
                session.save(myArticle);
//                System.out.println(date);
                tx.commit();

            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    private void save2myarticle3() {
        for (int i = 1; i <= 53; i++) {
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                Calendar calendar = Calendar.getInstance();
                calendar.set(2016, 3, 4);
                Date date = calendar.getTime();
//                date.toString().substring(0,10);
                MyArticle myArticle = new MyArticle();
                myArticle.setTitle(i + "《平凡的世界》最走心的27句话");
                myArticle.setAbstracts("生命里有着多少的无奈和惋惜，又有着怎样的愁苦和感伤？生活不能等待别人来安排，要自己去争取与奋斗……1991年的今天《平凡的世界》获第3届茅盾文学奖。");
                myArticle.setArticle_pic("http://p2.pstatp.com/large/3e60002cc4d1fa8655e");
                myArticle.setAuthor("陕西检察");
                myArticle.setContent(" " +
                        " <div><p>生命里有着多少的无奈和惋惜，又有着怎样的愁苦和感伤？生活不能等待别人来安排，要自己去争取与奋斗……1991年的今天《平凡的世界》获第3届茅盾文学奖。戳图↓↓送你《平凡的世界》里最走心的27句话，愿你在这平凡世界中能活出一番不平凡！<img src=\"http://p2.pstatp.com/large/3e60002cc4d1fa8655e\" img_width=\"440\" img_height=\"440\" alt=\"《平凡的世界》最走心的27句话\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p><img src=\"http://p1.pstatp.com/large/32500071a649d867a7a\" img_width=\"440\" img_height=\"440\" alt=\"《平凡的世界》最走心的27句话\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p><img src=\"http://p3.pstatp.com/large/3e60002cc4f003507b3\" img_width=\"440\" img_height=\"440\" alt=\"《平凡的世界》最走心的27句话\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p><img src=\"http://p3.pstatp.com/large/3e80002da5fd0daa9e5\" img_width=\"440\" img_height=\"440\" alt=\"《平凡的世界》最走心的27句话\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p><img src=\"http://p1.pstatp.com/large/3e60002cc507fdcfd38\" img_width=\"440\" img_height=\"440\" alt=\"《平凡的世界》最走心的27句话\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p><img src=\"http://p3.pstatp.com/large/3e4000444c4916d6efd\" img_width=\"440\" img_height=\"440\" alt=\"《平凡的世界》最走心的27句话\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p><img src=\"http://p3.pstatp.com/large/3e4000444c548055cc0\" img_width=\"440\" img_height=\"440\" alt=\"《平凡的世界》最走心的27句话\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p><img src=\"http://p1.pstatp.com/large/3e50004406467e159ce\" img_width=\"440\" img_height=\"440\" alt=\"《平凡的世界》最走心的27句话\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><p><img src=\"http://p3.pstatp.com/large/3e60002cc5224492752\" img_width=\"440\" img_height=\"440\" alt=\"《平凡的世界》最走心的27句话\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" >来源：人民网</p></div>" +
                        "");
                myArticle.setPub_date(date);
                int userIndex = (int) (1 + Math.random() * 14);
                myArticle.setMyClassification(session.get(MyClassification.class, userIndex));
                session.save(myArticle);
//                System.out.println(date);
                tx.commit();

            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    private void save2myarticle4() {
        for (int i = 1; i <= 53; i++) {
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                Calendar calendar = Calendar.getInstance();
                calendar.set(2016, 3, 4);
                Date date = calendar.getTime();
//                date.toString().substring(0,10);
                MyArticle myArticle = new MyArticle();
                myArticle.setTitle(i + "无人教你怎买无人机？这里有详尽的无人机购买指南");
                myArticle.setAbstracts("这阵子无人机风气大盛。大家见到网上不断有人贴出宏伟的航拍片段和照片，很想要吧？但是，无人机还是个很新的玩意，想找个老手指点一下亦不容易啊。");
                myArticle.setArticle_pic("http://p3.pstatp.com/large/40b000c581a3d3e66a2");
                myArticle.setAuthor("爱范儿 ");
                myArticle.setContent(" " +
                        "<p>这阵子无人机风气大盛。大家见到网上不断有人贴出宏伟的航拍片段和照片，很想要吧？但是，无人机还是个很新的玩意，想找个老手指点一下亦不容易啊。</p><p>不过，我们这里有最完整的无人机新手购买指南，让你可以愉快的选合适的无人机。</p><p></p><h3>享受无人机飞行</h3><p>不少消费者担心自己不懂操作无人机，但其实今天的的无人机已加入大量新科技，一点也不难操作。但是，也不是所有无人机都如有这些新技。不想因为不懂操作而搁在一旁，大家购买时就必须注意以下关键词：</p><p><u>遥控</u></p><p>要控制你的无人机，总要有一个遥控。目前遥控无人机的遥控，主要为三大种类：</p><p><img src=\"http://p3.pstatp.com/large/40b000c581a3d3e66a2\" img_width=\"1200\" img_height=\"800\" alt=\"无人教你怎买无人机？这里有详尽的无人机购买指南\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><ul><li>手机／平板遥控：目前愈来愈多无人机可用手机或平板，透过体感方式操作。相比起传统遥控器来说，它的好处是<strong>更小巧、更轻便</strong>、透过体感操作也更易上手；但它的坏处是<strong>信号很不稳定</strong>、长距离飞行很容易断线（在市郊空旷环境，一般在 50-100 米左右就会断线）；而且，它的操作虽然直观，但很难作出很精确的动作，换句话说是：<strong>易学难精</strong>。</li><li>传统遥控器操作：虽然愈来愈多无人机改用手机控制，但最正规的无人机仍然是以传统遥控器操控（部份遥控器可以与手机或平板配合使用）。相比手机来说，遥控器虽然比较笨重，也更难上手；<strong>但它的信号远较手机稳定，而且遥控距离比较远</strong>（一般遥控范围在在100 米或以上）。遥控器对于初学者比较难学，<strong>但当熟习后就更易进行复杂、精准的飞行动作</strong>。（上图）</li><li>手表、手环、语音控制等：近年开始有不少无人机生产商研发出各种新的遥控工具，但这些新技术目前还不够成熟，<strong>初学者尽可能避免这种遥控工具</strong>。</li></ul><p><u>自稳</u></p><p>新款的高阶无人机都懂得“自稳”：<strong>当它起飞后即使你什么也不做，什么也不碰，它也会在同一高度、同一位置悬停，不会飘来飘去</strong>，你推摇杆它会动、你一停手它就停下来。所以，即使你完全不懂怎样飞，也不怕飞丢了飞机，也有时间好整以暇的慢慢研究。不过，也并不是任何四轴飞行器都能自稳，你在挑选时必须满足以下条件…</p><ul><li>在户外自稳，无人机必须要同时有<strong> GPS 和气压计</strong>；</li><li>在室内自稳，无人机必须要同时有<strong>视觉定位和超声波定高</strong>；</li></ul><p><u>图传</u></p><p>当无人机飞高、飞远后，它就变得很细小、甚至小得连肉眼都看不见。在这时候我们可能看不到无人机在什么位置、也不知它向什么方向在飞，届时我们就很难控制无人机的飞行路线。我们想知道无人机看到什么，让我们更容易操作，<strong>所以我们需要“实时影像传送”，简称“图传”（下图），或有人称之为 FPV (First Person View)。</strong></p><p>我们买无人机的时候，除了要注意它是否有图传，还要注意：</p><p><img src=\"http://p3.pstatp.com/large/4140009f684a068b315\" img_width=\"770\" img_height=\"659\" alt=\"无人教你怎买无人机？这里有详尽的无人机购买指南\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><ul><li>图传距离：不少无人机厂商会标榜图传的最大距离，但<strong>实际上图传距离很容易受到干扰而严重缩短</strong>。以市郊空旷地区计算，一般以手机操作的无人机，图传距离多为 50-100 米左右，而传统遥控器操作的无人机，图传距离为 100-300 米左右，而高端的无人机，甚至可以远达 500米以上。<strong>购买无人机时，不可轻信厂商“最大距离”的宣传</strong>，最好能多了解一下产品的口碑。</li><li>图传画质：图传距离虽然重要，但图传画质一样重要：愈清晰的图传画质，就能让你更清楚的了解飞行的动态。目前，主流的无人机图传画质以 360-480p 为主，部份高级无人机亦有 720p 级别的高清图传画质。</li></ul><p></p><h3>买无人机来航拍</h3><p>对于绝大部份消费者来说，无人机最吸引的一定是“航拍”：无人机镜头下的景色，有多壮丽就多壮丽。不过，如果我们要买一台无人机来航拍，有什么地方值得注意呢？</p><p><u>镜头</u></p><p>尽管今天的拍摄技术发展一日千里，但航拍机由于承重的限制，它们绝大部份都不能使用专业镜头，虽然厂家可能会标榜各种参数，但结果成像质素一甚为参差。</p><ul><li>画质：尽管不少无人机都标榜自己有 4k 或 1080p 的超高清画质，但其实不少无人机的实际画质仍不理想。购买时除了注意分辨率之外，亦可多关注以及低光环境下的拍摄效果，以及影响视频流畅度的“帧率” (FPS)（4k 应不少于 24 格，1080p 应不少于 30 格为佳）。</li><li>视野：由于航拍发烧友都喜欢更广阔壮丽的风景，所以不少无人机都会配备广角镜。但一般高广角的摄像镜 （100 – 170 度视野）都会使画面严重变形（直线变成弧线），但标榜不变形的广角镜头，视野一般比较狭窄（80-100 度）。</li></ul><p><u>云台</u></p><p>如果你要拍航拍视频的话，影象稳定器，即云台 (Gimbal) 是必须的（下图）。由于无人机在飞行时经常有很大幅的摆动，如果没有云台的帮助，拍出来的视频会变成过山车一样的摇摆不定。而且，好的云台可以让你调校摄拍角度，让你在天上垂直俯瞰地面。</p><img src=\"http://p3.pstatp.com/large/417000317e615067eea\" img_width=\"1199\" img_height=\"797\" alt=\"无人教你怎买无人机？这里有详尽的无人机购买指南\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ><ul><li>三轴云台：这种<strong>物理性的云台（上图）能提供最佳的影像稳定效果</strong>，但是它的成本比较高，而且一摔就坏；另外，<strong>由于它的结构问题，会使无人机变得更笨重，难以安稳的收纳</strong>。</li><li>电子云台：透过电子防抖技术，把抖震的画面稳定化，但目前无人机相机的防抖技术还未十分成熟，<strong>防抖能力仍旧有限</strong>；视频经过防抖处理，若干程度上也会影响画质。不过，<strong>它的最大好处是轻巧</strong>、也不容易摔坏。</li></ul><p></p><h3>带无人机旅游</h3><p>很多时我们在旅行时，都会特意带备相机去记录每个精彩的时刻；而近年无人机流行后，也愈来愈多人带无人机去旅行，透过航拍来发现更美丽的风光。那我们在选择旅行用无人机时，又有什么要注意的？</p><p><u>便携</u></p><p>旅行用无人机，重点当然是要便携。但无人机并不是愈轻便就愈好：</p><ul><li>大型无人机：对角线轴距由 350mm（零度 XIRO Xplorer 4k）至 581mm（大疆 Inspire 1 Pro）左右。<strong>无人机轴距愈大、愈笨重，但轴距愈大、抗风力也愈强，稳定性亦较佳，无论是飞行或是航拍，也更为安心；</strong></li><li>小型无人机：对角线轴距由 250mm（哈博森 H501s）到 290mm（派洛特 Bebop 2）左右。轴距愈小<strong>的无人机愈易携带，但抗风力相对较弱，稳定性亦会较差，飞行时会不太放心，也会影响航拍质素。</strong></li></ul><p><u>续航</u></p><p>大家都想在旅行时尽情飞行，但是目前无人机的续航情况还是很不理想，故此旅行时不想白带没电飞行的无人机，就要多注意续航力的问题：</p><img src=\"http://p3.pstatp.com/large/40b000c581cc145b4b4\" img_width=\"1199\" img_height=\"797\" alt=\"无人教你怎买无人机？这里有详尽的无人机购买指南\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ><ul><li>电池本身：一般高阶无人机飞行时间只有在 15-30 分钟左右，中低阶的无人机续航力往往不到十分钟，打算带无人机去旅行的，可能要多准备几块电池；然而，目前无人机电池售昂贵，而且也有一定重量，购买无人机时，必须时考虑备用电池的成本。</li><li>电池充电：目前只有少量低阶无人机，才能用 USB 或充电宝之类的设备来充电；绝大部份高阶无人机的电池，都必须使用专属充电器；因此，不要以为带个 USB 充电宝，就能不用带电池或充电器。</li></ul><p></p><h3>无人机选购建议</h3><p><u>一般玩家之选</u></p><p><img src=\"http://p10.pstatp.com/large/40d000c373f9c007f22\" img_width=\"1200\" img_height=\"474\" alt=\"无人教你怎买无人机？这里有详尽的无人机购买指南\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><ul><li>Phantom 4：刚推出的它拥有目前无人机界别最强的 5km 图传距离，也有目前无人机界别唯一的双目跟随、双目避障功能，加上它拥有优秀的 4k 拍摄相机和同级最长的续航时间，使它成为<strong>目前平均能力值最高的无人机</strong>。尽管它的最大弱点是较高昂的售价（官方定价：8999 元）和相对笨重的身型，但仍然无阻它成为目前最受欢迎的无人机。</li></ul><p><img src=\"http://p3.pstatp.com/large/3c9000c5b31ceafb425\" img_width=\"1200\" img_height=\"474\" alt=\"无人教你怎买无人机？这里有详尽的无人机购买指南\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><ul><li>XIRO Xplorer 4k：去年推出的 XIRO Xplorer 虽然因贫弱的图传和不太理想的摄像镜而被垢病，但它仍以超酷的外型和很便携的可拆卸式设计，吸引了不少入门玩家。今年零度更为它加配全新的 1km 图传和 4k 云台，大幅改善它的软肋，而且它的镜头还可以拆出来当作手持云台使用。也许它在整体上仍然不及 Phantom 4，但官方 3999 的定价和相对便携的设计，<strong>使它可能是目前性价比最高的无人机</strong>。</li></ul><p><u>大型航拍机之选</u></p><p><img src=\"http://p1.pstatp.com/large/40b000c581d735efcf5\" img_width=\"1200\" img_height=\"474\" alt=\"无人教你怎买无人机？这里有详尽的无人机购买指南\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><ul><li>Inspire 1 Pro：<strong>目前市面上拍摄能力最强的航拍机，没有之一。</strong>Inspire 1 Pro 虽然是最重、最笨重、也是最昂贵（官方定价：17999 元）的消费级无人机，但如果想要最专业的航拍质素，目前消费级无人机当中，就只有 Inspire 1 Pro 能使用无反相机级别的摄像镜头。你要成为最专业的航拍专家？Inspire 1 Pro 不会让你失望。</li></ul><p><img src=\"http://p3.pstatp.com/large/40b000c581f71d1a201\" img_width=\"1200\" img_height=\"474\" alt=\"无人教你怎买无人机？这里有详尽的无人机购买指南\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><ul><li>Yuneec Q500 4k：Q500 4k 的重量与一般 350mm 级别的无人机相若， 但其 550mm 的巨大轴距让它成为最稳定的消费级无之一。它同样拥有 4k 镜头，亦与 XIRO Xplorer 4k 一样，能够把镜头拆下来当手持云台。也许它没有 Inspire 1 Pro 的强大，但相对轻盈的机身和相对低廉的售价（官方天猫店：5288 元），会是<strong>专业摄影师首次踏足航拍的入门选择</strong>。</li></ul><p><u>旅游便携之选</u></p><p><img src=\"http://p2.pstatp.com/large/4150009f76a77ac614d\" img_width=\"1200\" img_height=\"474\" alt=\"无人教你怎买无人机？这里有详尽的无人机购买指南\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><ul><li>Bebop 2：派洛特 (Parrot) 是著名的海外无人机公司，他们在去年刚推出的 Bebop 2，是目前少数能提供 20 分钟以上优异续航力的无人机，而它在官方天猫店的 3998 元的售价亦算廉宜， 290mm 的娇小身驱和同级最强的电子云台，使它能在极度便携下仍然有不错的拍摄能力，<strong>可以说是目前最便携的航拍无人机</strong>。但它必须透过手机 WiFi 连上无人机，信号极容易受到干扰，用户很难放心进行长距离飞行；如果你要比较安心的飞行，就必须要购买一个更笨重、也更昂贵的 Skycontroller 专属遥控器。</li></ul><p><img src=\"http://p3.pstatp.com/large/4140009f686ed43c2e1\" img_width=\"1200\" img_height=\"474\" alt=\"无人教你怎买无人机？这里有详尽的无人机购买指南\" \n" +
                        "onerror=\"javascript:errorimg.call(this);\" ></p><ul><li>H501s：哈博森 (Hubsan) 的这台无人机机身虽小，但五脏俱存：比 Bebop 2 身躯更细小的它 (250mm 轴距），里面仍然拥有 GPS 和气压计等高阶无人机配备。可是，它需要使用传统遥控器，所以仍不如 Bebop 2 的便携，但也由于使用传统遥控器，让它比 Bebop 2 飞得更高更远。它的最大缺点是没有任何物理或电子云台，使它拍的视频质素惨不忍睹。虽然如此，藉着轻巧性的身躯，和官方天猫店 1499 元的低廉售价，<strong>使它成为目前最佳的入门无人机</strong>。</li></ul>" +
                        "");
                myArticle.setPub_date(date);
                int userIndex = (int) (1 + Math.random() * 14);
                myArticle.setMyClassification(session.get(MyClassification.class, userIndex));
                session.save(myArticle);
//                System.out.println(date);
                tx.commit();

            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    private void save2mycomment() {
        List<String> comments = new ArrayList<>();
        comments.add("致敬王勇平！既履行了职责又守住了做人的底线！不明白为什么当时那么多人指责他？难道就听不出来那些话的意思吗？");
        comments.add("在中国要做好新闻发言人，好难的c");
        comments.add("在中国要做好新闻发言人，好难的c");
        comments.add("房子能拉动的装修家具灯具钢筋汽车石材家电等生意，别尼玛在那手插双腰在那喊这个国家房子怎么怎么有希望，有你这样的喷子有希望才怪！现在租房都头疼了");
        comments.add("平均1干万的房子3百多套1天抢光纯属炒作。");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 3, 4);
        Date date = calendar.getTime();

        for (int i = 1; i < 2053; i++) {
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();

                int commentIndex = (int) (1 + Math.random() * 5);
                int userIndex = (int) (1 + Math.random() * 100);
                int articleIndex = (int) (1 + Math.random() * 800);
//                System.out.println(commentIndex);
                MyComment myComment = new MyComment("comment" + i + ":::" + comments.get(commentIndex - 1), date);
                myComment.setMyUser(session.get(MyUser.class, userIndex));
                myComment.setMyArticle(session.get(MyArticle.class, articleIndex));
                session.save(myComment);

                tx.commit();

            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    private void save2commentlike2() {
        Session session = null;
        Transaction tx = null;
        for (int i = 1; i < 10000; i++) {
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();

                int userIndex = (int) (1 + Math.random() * 100);
                int commentIndex = (int) (1 + Math.random() * 1500);

                CommentLike commentLike = new CommentLike();
                commentLike.setMyComment(session.get(MyComment.class, commentIndex));
                commentLike.setMyUser(session.get(MyUser.class, userIndex));

                session.save(commentLike);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    private void save2articlelike() {
        for (int i = 1; i < 3424; i++){
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();

                int userIndex = (int) (1 + Math.random() * 100);
                int articleIndex = (int) (1 + Math.random() * 800);

                ArticleLike articlelike = new ArticleLike();
                articlelike.setMyUser(session.get(MyUser.class, userIndex));
                articlelike.setMyArticle(session.get(MyArticle.class, articleIndex));

                session.save(articlelike);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    private void save2articledislike() {
        for (int i = 1; i < 3424; i++){
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();

                int userIndex = (int) (1 + Math.random() * 100);
                int articleIndex = (int) (1 + Math.random() * 800);

                ArticleDisLike articledislike = new ArticleDisLike();
                articledislike.setMyUser(session.get(MyUser.class, userIndex));
                articledislike.setMyArticle(session.get(MyArticle.class, articleIndex));

                session.save(articledislike);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    private void save2mysubscribe() {
        for (int i = 0; i < 500; i++) {
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();

                int classifiIndex = (int)(1+Math.random()*14);
                int userIndex = (int)(1+Math.random()*100);

                List<MySubscribe> mySubscribe = session.createQuery("from MySubscribe s where s.myUser.user_id = :id and s.myClassification.classification_id = :classId")
                        .setInteger("id",userIndex)
                        .setInteger("classId", classifiIndex)
                        .list();
//                System.out.println(mySubscribe.size()+"----------------");
                if (mySubscribe.size()==0){
                    MySubscribe mySubscibe = new MySubscribe();
                    mySubscibe.setMyClassification(session.get(MyClassification.class, classifiIndex));
                    mySubscibe.setMyUser(session.get(MyUser.class,userIndex));
                    session.save(mySubscibe);
                }

                tx.commit();
            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }

    }

    private void save2mycollo() {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            for (int i = 0; i < 1023; i++) {
                int userIndex = (int)(1+Math.random()*100);
                int articleIndex = (int)(1+Math.random()*800);
                MyCollocation myCollocation = new MyCollocation();
                myCollocation.setMyUser(session.get(MyUser.class,userIndex));
                myCollocation.setMyArticle(session.get(MyArticle.class,articleIndex));
                session.save(myCollocation);
            }

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    private void save2emdia0(){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Media media = new Media();
            media.setConent("<p>“今日头条”是一款基于数据挖掘的推荐引擎产品，是国内移动互联网领域成长最快的产品服务之一。“今日头条”第一个版本于2012年8月上线，截至2016年2月，“今日头条”已经在为超过4亿的忠诚用户服务，每天有超过4000万的用户在头条上找到让他们了解世界、启发思考、开怀一笑的信息，并活跃地参与互动。</p>\n" +
                    "        <p>“今日头条”的团队是一支拥有丰富创业及成熟公司经验的靠谱团队，聚集了来自一流学校和一流公司的顶尖人才，在推荐引擎、机器学习等技术领域拥有与世界级公司接轨的能力。公司正处于高速发展期，2014年6月，今日头条获得C轮1亿美元的融资。</p>\n" +
                    "        <p>在人们的注意力从电脑屏幕向手持设备迁移的过程中，信息的产生、发布、流传、消费也都在经历巨大的变革：我们早就不再订阅传统的期刊杂志，甚至已经不再浏览传统的新闻网站；越来越多的人在写博客、刷微博、通过分享、转发、评论、点赞、点踩的行为来表达自己与信息之间的关系；我们花在手机上的时间比在电脑上还多了，很多时候我们在吃饭的时候都在看手机…… “今日头条”是为移动互联网而生的，是一个新型媒体的探路先锋，致力于在新的信息时代里给人们提供一个与众不同的，高效简洁的信息获取和分享的平台。</p>\n" +
                    "        <p>信息爆炸早已不是一个新词，传统的人工编辑无法可靠地筛选出热门高质量的新闻资讯。“今日头条”通过对资讯在社交网站上的传播情况以及成千上万个网站的数据挖掘，智能地分析出每时每刻最热门最值得你知道的资讯。</p>\n" +
                    "        <p>你想看的资讯和我想看的肯定也不同，当你用微博等社交账号登录“今日头条”时，它能迅速解读你的兴趣DNA，无需复杂的选择的订阅，即会猜出你感兴趣的文章；而且你用得越多，“今日头条”就越懂你，你会得到你更喜欢的文章。</p>\n" +
                    "        <p>使用“今日头条”，资讯的消费也变得更社会化，你可以在这里看到好友或名人的阅读动态，看到最全面和最优选的评论。</p>\n" +
                    "        <p>请下载我们的主打应用 今日头条（iPhone、Android版）到你的手机上，不再错过任何你感兴趣的事！</p>\n" +
                    "        <p>你关心的，才是头条！</p>\n");
            session.save(media);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    private void save2emdia1(){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Media media = new Media();
            media.setConent("<ul id=\"contact\" class=\"clearfix\">\n" +
                    "            <li class=\"big\">\n" +
                    "                    <a href=\"http://e.weibo.com/headlineapp\" target=\"_blank\"><img src=\"http://s0.pstatp.com/r2/image/helper/sina_connect.png\">\n" +
                    "                    </a>\n" +
                    "                    <a href=\"http://t.qq.com/headlineapp\" target=\"_blank\"><img src=\"http://s0.pstatp.com/r2/image/helper/tencent_connect.png\">\n" +
                    "                    </a>\n" +
                    "            </li>\n" +
                    "\n" +
                    "            \n" +
                    "            \n" +
                    "            <li>\n" +
                    "                <span class=\"first\">市场、商务合作请联系邮箱 :</span>\n" +
                    "                <span><strong><a href=\"mailto:bd@toutiao.com\">bd@toutiao.com</a></strong>\n" +
                    "                </span>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <span class=\"first\">广告合作请联系邮箱 :</span>\n" +
                    "                <span><strong><a href=\"mailto:ad@toutiao.com\">ad@toutiao.com</a></strong>\n" +
                    "                </span>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <span class=\"first\">广告投放请访问 :</span>\n" +
                    "                <span><strong><a href=\"http://ad.toutiao.com/e/\">http://ad.toutiao.com/e/</a></strong>\n" +
                    "                </span>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <span class=\"first\">媒体合作请联系邮箱 :</span>\n" +
                    "                <span><strong><a href=\"mailto:media@toutiao.com\">media@toutiao.com</a></strong>\n" +
                    "                </span>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <span class=\"first\">媒体采访请联系邮箱 :</span>\n" +
                    "                <span><strong><a href=\"mailto:pr@toutiao.com\">pr@toutiao.com</a></strong>\n" +
                    "                </span>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <span class=\"first\">用户反馈请联系邮箱 :</span>\n" +
                    "                <span><strong><a href=\"mailto:kefu@toutiao.com\">kefu@toutiao.com</a></strong>\n" +
                    "                </span>\n" +
                    "            </li>\n" +
                    "            <li>\n" +
                    "                <span class=\"first\">头条号问题请联系邮箱 :</span>\n" +
                    "                <span><strong><a href=\"mailto:mp@toutiao.com\">mp@toutiao.com</a></strong>\n" +
                    "                </span>\n" +
                    "            </li>\n" +
                    "</ul>");
            session.save(media);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    private void save2emdia2(){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Media media = new Media();
            media.setConent("p>本《用户协议》（以下简称“本协议”）是您(以下也可以“用户”来指代您)与北京字节跳动科技有限公司(以下简称“字节跳动”)就使用“今日头条”产品所达成的协议。在您开始使用“今日头条”产品之前，请您务必应认真阅读(未成年人应当在监护人陪同下阅读)并充分理解本协议，特别涉及是免除或者限制字节跳动责任的条款和开通和使用特殊单项服务的条款。</p>\n" +
                    "\n" +
                    "        <p>除非您完全接受本协议的全部内容，否则您无权使用“今日头条”产品。若您使用“今日头条”产品，则视为您已充分理解本协议并承诺作为协议的一方当事人接受协议的约束。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>1、协议适用范围</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>1.1 本协议约定了字节跳动和用户之间就使用“今日头条”事宜发生的权利义务关系。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>1.2 “字节跳动”指北京字节跳动科技有限公司。但就本协议涉及的某些服务项目，字节跳动的关联企业也可能向您提供服务，与您发生权利义务关系。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>1.3 用户指任何以合法的方式获取和使用“今日头条”产品的人。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>1.4 “今日头条”产品指一款由字节跳动合法拥有并运营的、标注名称为“今日头条”的移动客户端应用程序及以及对应的域名为\n" +
                    "\n" +
                    "            “m.toutiao.com”\n" +
                    "\n" +
                    "            的移动网站。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>2、使用“今日头条”产品</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>2.1 用户可以从任何合法的渠道下载“今日头条”软件程序到其合法拥有的终端设备中。但除非得到特别的授权，否则，用户不得以任何形式改编、复制或交易“今日头条”软件程序。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>2.2 一旦用户在其终端设备中打开“今日头条”，即视为使用“今日头条”产品。为充分实现“今日头条”的全部功能，用户可能需要将其终端设备联网。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>2.3 字节跳动授权用户拥有中华人民共和国境内永久地、不可转让的、非独占地和非商业性地使用“今日头条”产品的权利，但该权利不可转让，字节跳动也保留在任何必要情形下收回该授权的权利。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>2.4 您无需注册即可开始使用“今日头条”产品。但某些功能和某些单项服务项目，可能要求您必须注册后才能使用。此时，您若想要使用这些功能或服务，则应当完成注册并以注册用户的身份登录。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>3、关于“帐号”</p>\n" +
                    "        <p>3.1 “今日头条”为用户提供了注册通道，用户在有权选择合法的字符组合作为自己的帐号，并自行设置符合安全要求的密码。用户设置的帐号、密码是用户用以登录“今日头条”，并以注册用户身份使用产品或接受服务的凭证。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>3.2 您在“今日头条”中的注册账号仅限于您本人使用，禁止赠与、借用、出租或售卖。如果字节跳动发现或者有理由怀疑使用者并非帐号初始注册人，则有权在未经通知的情况下，暂停或终止向该注册账号提供服务，并有权注销该帐号，而无需向注册该帐号的用户承担法律责任。由此带来的包括并不限于用户通讯中断、用户资料和信息等清空等损失由用户自行承担。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>3.3 用户有责任维护个人帐号、密码的安全性与保密性，用户就以其注册帐号名义所从事的一切活动负全部责任，包括用户数据的修改、发表的言论以及其他在“今日头条”客户端上的操作行为。因此，用户应高度重视对帐号与密码的保密，若发现他人未经许可使用其帐号或发生其他任何安全漏洞问题时立即通知字节跳动。但无论因何种原因发生的密码泄露，均应由用户自行承担责任。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>3.4 用户帐号在丢失或遗忘密码后，可遵照字节跳动的申诉途径及时申诉请求找回帐号。用户应提供能增加帐号安全性的个人密码保护资料。用户可以凭初始注册资料及个人密码保护资料填写申诉单向字节跳动申请找回帐号，字节跳动的密码找回机制仅负责识别申诉单上所填资料与系统记录资料的正确性，而无法识别申诉人是否系真正帐号有权使用人。对用户因被他人冒名申诉而致的任何损失，字节跳动不承担任何责任，用户知晓帐号及密码保管责任在于用户，字节跳动并不承诺帐号丢失或遗忘密码后用户一定能通过申诉找回帐号。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>3.5 用户应保证注册帐号时填写的身份信息的真实性，任何由于非法、不真实、不准确的用户信息所产生的责任由用户承担。用户应不断更新注册资料，符合及时、详尽、真实、准确的要求。所有原始键入的资料将被引用为用户的帐号注册资料。如果因用户的注册信息不真实而引起的问题，以及对问题发生所带来的后果，字节跳动不负任何责任。如果用户提供的信息不准确、不真实、不合法或者字节跳动有理由怀疑为错误、不实或不合法的资料或在个人资料中发布广告、不严肃内容及无关信息，字节跳动有权暂停或终止向用户提供服务，注销该帐号，并拒绝用户现在和未来使用“今日头条”产品的全部或任何部分。因此产生的一切损失由用户自行承担。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>3.6 除自行注册“今日头条”帐号外，用户也可选择通过“今日头条”的帐号管理后台，授权使用其合法拥有的新浪微博、腾讯微博、腾讯QQ、人人网、开心网的帐号注册并登录“今日头条”。当用户以上述已有账号注册的，同样适用本协议中对账号的相关约定。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>4、用户行为的合法性要求</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>4.1 用户在使用“今日头条”的产品时，应当遵守中华人民共和国的法律。用户不得利用字节跳动服务产品从事上述法律法规、政策以及侵犯他人合法权利的行为。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>4.2 您不得使用未经字节跳动授权或许可的任何插件、外挂或第三方工具对“今日头条”产品的正常运行进行干扰、破坏、修改或施加其他影响。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>4.3 您不得利用或针对“今日头条”产品进行任何危害计算机网络安全的行为，包括但不限于：使用未经许可的数据或进入未经许可的服务器/帐户；未经允许进入公众计算机网络或者他人计算机系统并删除、修改、增加存储信息；未经许可，企图探查、扫描、测试“今日头条”产品系统或网络的弱点或其它实施破坏网络安全的行为；企图干涉、破坏“今日头条”产品系统或网站的正常运行，故意传播恶意程序或病毒以及其他破坏干扰正常网络信息服务的行为；伪造TCP/IP数据包名称或部分名称。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>4.4 未经字节跳动许可，您不得在“今日头条”中进行任何诸如发布广告、销售商品的商业行为。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>4.5 在任何情况下，如果字节跳动有理由认为用户的任何行为违反或可能违反上述约定的，字节跳动可在任何时候不经任何事先通知终止向用户提供服务。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>5、用户个人信息保护</p>\n" +
                    "        <p>5.1 您在注册帐号或使用“今日头条”产品的过程中，可能需要填写一些必要的信息。若国家法律法规有特殊规定的，您需要填写真实的身份信息。若您填写的信息不完整，则无法使用相关产品或服务或在使用过程中受到限制。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>5.2 您可随时浏览、修改自己提交的个人身份信息，但出于安全性和身份识别（如号码申诉服务）的考虑，您可能无法修改注册时提供的初始注册信息及其他验证信息。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>5.3字节跳动将运用各种安全技术和程序建立完善的管理制度来保护您的个人信息，以免遭受未经授权的访问、使用或披露。但用户同时明白，互联网的开放性以及技术更新非常快，非字节跳动可控制的因素导致用户信息泄漏的，字节跳动不承担责任。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>5.4 字节跳动不会将您的个人信息转移或披露给任何非关联的第三方，除非：</p>\n" +
                    "        <p>　　（1）相关法律法规或法院、政府机关要求；或</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>　　（2）为完成合并、分立、收购或资产转让而转移；或</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>　　（3）为提供您要求的服务所必需。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>5.5请用户注意勿在使用“今日头条”中透露自己的各类财产帐户、银行卡、信用卡、第三方支付账户及对应密码等重要资料，否则由此带来的任何损失由用户自行承担。用户不应将自认为隐私的信息通过“今日头条”发表、上传或扩散。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>5.6 在以下情况下，字节跳动有权使用用户的个人信息：</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>1)在进行促销或抽奖时，字节跳动可能会与赞助商共享用户的个人信息，在这些情况下字节跳动会在发送用户信息之前进行提示，并且用户可以通过不参与促销或抽奖活动来终止传送过程；</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>2)字节跳动可能会与第三方合作向用户提供相关的网络服务，在此情况下，如该第三方同意承担与字节跳动同等的保护用户隐私的责任，则字节跳动有权将用户的注册资料等提供给该第三方；</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>3)在不透露单个用户隐私资料的前提下，字节跳动有权对整个用户数据库进行分析并对用户数据库进行商业上的利用。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>字节跳动利用您提交的电子邮件用于发送与字节跳动有关的通知。除前述使用以及您个人行为或同意披露外，字节跳动不会在网络中向任何第三方显示或共享您的电子邮件信息。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>6、用户跟帖评论规则</p>\n" +
                    "        <p>6.1 用户在注册后，可以以注册账号登录“今日头条”并对所阅读的内容发表评论。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>6.2 字节跳动致力使跟帖评论成为文明、理性、友善、高质量的意见交流。在推动跟帖评论业务发展的同时，不断加强相应的信息安全管理能力，完善跟帖评论自律，切实履行社会责任，遵守国家法律法规，尊重公民合法权益，尊重社会公序良俗。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>6.3 用户应当以真实身份信息注册账号、使用跟帖评论服务。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>6.4字节跳动承诺、并诚请所有用户，使用跟帖评论服务将自觉遵守不得逾越法律法规、社会主义制度、国家利益、公民合法权益、社会公共秩序、道德风尚和信息真实性等“七条底线”。</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>6.5 用户不得发表下列信息：</p>\n" +
                    "\n" +
                    "\n" +
                    "        <p>（1）反对宪法确定的基本原则的；</p>\n" +
                    "\n" +
                    "        <p>（2）危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；</p>\n" +
                    "\n" +
                    "        <p>（3）损害国家荣誉和利益的；</p>\n" +
                    "\n" +
                    "        <p>（4）煽动民族仇恨、民族歧视，破坏民族团结的；</p>\n" +
                    "\n" +
                    "        <p>（5）煽动地域歧视、地域仇恨的；</p>\n" +
                    "\n" +
                    "        <p>（6）破坏国家宗教政策，宣扬邪教和迷信的；</p>\n" +
                    "\n" +
                    "        <p>（7）散布谣言，扰乱社会秩序、破坏社会稳定的；</p>\n" +
                    "\n" +
                    "        <p>（8）散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；</p>\n" +
                    "\n" +
                    "        <p>（9）侮辱或者诽谤他人，侵害他人合法权益的；</p>\n" +
                    "\n" +
                    "        <p>（10）对他人进行暴力恐吓、威胁，实施人肉搜索的；</p>\n" +
                    "\n" +
                    "        <p>（11）未获得未满18周岁未成年人法定监护人的书面同意，传播该未成年人的隐私信息的；</p>\n" +
                    "\n" +
                    "        <p>（12）散布污言秽语，损害社会公序良俗的；</p>\n" +
                    "\n" +
                    "        <p>（13）侵犯他人知识产权的；</p>\n" +
                    "\n" +
                    "        <p>（14）散布商业广告，或类似的商业招揽信息；</p>\n" +
                    "\n" +
                    "        <p>（15）使用本网站常用语言文字以外的其他语言文字评论的；</p>\n" +
                    "\n" +
                    "        <p>（16）与所评论的信息毫无关系的；</p>\n" +
                    "\n" +
                    "        <p>（17）所发表的信息毫无意义的，或刻意使用字符组合以逃避技术审核的；</p>\n" +
                    "\n" +
                    "        <p>（18）法律、法规和规章禁止传播的其他信息。</p>\n" +
                    "\n" +
                    "        <p>6.6 对违反上述承诺的用户，字节跳动将视情况采取预先警示、拒绝发布、删除跟帖、短期禁止发言直至永久关闭账号等管理措施。对涉嫌违法犯罪的跟帖评论将保存在案、并在接受有关政府部门调查时如实报告。</p>\n" +
                    "\n" +
                    "        <p>6.7 字节跳动将完善公众举报平台，欢迎用户监督举报各类不法传播活动和违法有害信息，以共同营造清朗的网络空间。</p>\n" +
                    "\n" +
                    "        <p>7、服务的变更、中断和终止</p>\n" +
                    "\n" +
                    "        <p>7.1您理解并同意，字节跳动提供的“今日头条”产品和服务是按照现有技术和条件所能达到的现状提供的。字节跳动会尽最大努力向您提供服务，确保服务的连贯性和安全性；但字节跳动不能随时预见和防范法律、技术以及其他风险，包括但不限于不可抗力、病毒、木马、黑客攻击、系统不稳定、第三方服务瑕疵、政府行为等原因可能导致的服务中断、数据丢失以及其他的损失和风险。</p>\n" +
                    "\n" +
                    "        <p>7.2 用户须明白，字节跳动为了服务整体运营的需要，有权在公告通知后，在不事先通知用户的情况下修改、中断、中止或终止“今日头条”产品内的各项服务，而无须向用户或第三方负责或承担任何赔偿责任。</p>\n" +
                    "\n" +
                    "        <p>8、知识产权</p>\n" +
                    "\n" +
                    "        <p>8.1 未经相关权利人同意，用户不得对“今日头条”的产品和服务涉及的相关网页、应用、软件等产品进行反向工程、反向汇编、反向编译等。</p>\n" +
                    "\n" +
                    "        <p>8.2 用户在使用“今日头条”产品和服务时发表上传的文字、图片、视频、软件以及表演等用户原创的信息，此知识产权归属用户，但用户的发表、上传行为表明该信息对字节跳动非独占性、永久性和可转让的授权。字节跳动可将上述信息在“今日头条”中使用，可在字节跳动的其他产品中使用，也可以由字节跳动授权给合作方使用。</p>\n" +
                    "\n" +
                    "        <p>8.3 用户应保证，在使用“今日头条”的产品和服务时上传的文字、图片、视频、软件以及表演等的信息不侵犯任何第三方知识产权。否则，字节跳动有权移除该侵权信息，并对此不负任何责任。前述第三方提出权利主张，用户应自行承担责任，并保证字节跳动不会因此而遭受任何损失。</p>\n" +
                    "\n" +
                    "        <p>9、法律责任声明</p>\n" +
                    "\n" +
                    "        <p>9.1 字节跳动对于任何自本网站而获得的他人的信息、内容或者广告宣传等任何资讯(以下统称“信息”)，不负保证真实、准确和完整性的责任。如果任何单位或者个人通过上述“信息”而进行任何行为，须自行甄别真伪和谨慎预防风险，否则，无论何种原因，字节跳动不对任何非与本网站直接发生的交易和(或)行为承担任何直接、间接、附带或衍生的损失和责任。</p>\n" +
                    "\n" +
                    "        <p>9.2 字节跳动不保证(包括但不限于)：</p>\n" +
                    "\n" +
                    "        <p>（1）字节跳动完全适合用户的使用要求；</p>\n" +
                    "\n" +
                    "        <p>（2）字节跳动不受干扰，及时、安全、可靠或不出现错误；用户经由字节跳动取得的任何产品、服务或其他材料符合用户的期望；</p>\n" +
                    "\n" +
                    "        <p>（3）软件中任何错误都将能得到更正。</p>\n" +
                    "\n" +
                    "        <p>9.3 基于以下原因而造成的利润、商业信誉、资料损失或其他有形或无形损失，字节跳动不承担任何直接、间接、附带、特别、衍生性或惩罚性的赔偿责任(即使字节跳动事先已被告知发生此种赔偿之可能性亦然)：</p>\n" +
                    "\n" +
                    "        <p>（1）对字节跳动的使用或无法使用；</p>\n" +
                    "\n" +
                    "        <p>（2）字节跳动不受干扰，及时、安全、可靠或不出现错误；用户经由字节跳动取得的任何产品、服务或其他材料符合用户的期望；</p>\n" +
                    "\n" +
                    "        <p>（3）软件中任何错误都将能得到更正。</p>\n" +
                    "\n" +
                    "        <p>（4）任何第三方在本服务中所作的声明或行为；</p>\n" +
                    "\n" +
                    "        <p>（5）用户基于在“今日头条”中发布的广告信息，向第三方购买商品或服务；</p>\n" +
                    "\n" +
                    "        <p>（6）其他与字节跳动相关的事宜，但本用户协议有明确规定的除外。</p>\n" +
                    "\n" +
                    "        <p>10、关于单项服务的特殊约定</p>\n" +
                    "\n" +
                    "        <p>10.1 “今日头条”产品中包含字节跳动以各种合法方式获取的新闻内容或新闻链接，同时也包括字节跳动或其关联企业合法运营的其他单项服务。这些服务以“今日头条”特别频道的形式存在，包括但不限于“段子”、“美女”、“趣图”、“语录”、“特卖”和“彩票”等。字节跳动将不时地增加、减少或改动这些特别频道的设置。</p>\n" +
                    "\n" +
                    "        <p>10.2 您可以在“今日头条”中开启和使用上述单项服务功能。某些单项服务可能需要您同时接受就该服务特别制订的用户协议、使用协议或者其他约束您与该项服务提供者之间的法律文件。字节跳动将以醒目的方式提供这些法律文件供您查阅。但一旦您开始使用上述服务，则视为您同时接受有关单项服务的相关法律文件的约束。</p>\n" +
                    "\n" +
                    "        <p>11、违约责任</p>\n" +
                    "\n" +
                    "        <p>用户因违反国家法律法规或本协议的约定或用户侵害他人任何权利因而衍生或导致任何第三人向字节跳动提出任何索赔或请求，包括但不限于诉讼费用、律师费用、差旅费用、和解金额、罚款或生效法律文书中规定的损害赔偿金额、软件使用费等而给字节跳动造成损失的，用户应赔偿字节跳动因此而遭受的一切损失，并消除影响。</p>\n" +
                    "\n" +
                    "        <p>12、其他条款</p>\n" +
                    "\n" +
                    "        <p>12.1本协议的订立、执行和解释及争议的解决均应适用中国法律。倘本协议之任何规定因与中华人民共和国法律抵触而无效，则这些条款将尽可能接近本协议原条文意旨重新解析，且本协议其它规定仍应具有完整的效力及效果。本协议的签署地点为字节跳动公司所在地北京市海淀区，若用户与字节跳动发生争议的，双方同意将争议提交北京市海淀区法院诉讼解决。</p>\n" +
                    "\n" +
                    "        <p>12.2本协议可能因国家政策、产品以及履行环境发生变化而进行修改，字节跳动会将修改后的协议发布在网站上。若您对修改后的协议有异议的，请立即停止登录、使用“今日头条”产品及服务，若您登录或继续使用“今日头条”产品及服务，视为认可修改后的协议。</p>\n");
            session.save(media);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    private void save2emdia3(){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Media media = new Media();
            media.setConent("<p>“今日头条”是一款基于数据挖掘技术的推荐引擎产品，它为用户推荐有价值的、个性化的信息，提供连接人与信息的新型服务，是国内移动互联网领域成长最快的产品之一。鉴于第三方在平台发布信息数量庞大，今日头条作为网络服务提供者，为有效保护权利人，对于其中可能涉嫌侵犯权利人合法权益的信息，根据相关法律法规，特制定本指引。如果您作为权利人认为今日头条平台上的信息侵犯了您的合法权益，您可依据本指引通知我们，我们将依法处理。</p>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        <h1 id=\"一-投诉流程\">一、 投诉流程</h1>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        <h2 id=\"1投诉通知\">1、投诉通知</h2>\n" +
                    "\n" +
                    "        <p>权利人认为今日头条平台上的某些内容涉嫌侵犯其合法权益，可向今日头条发出书面投诉通知。根据《信息网络传播权保护条例》及《最高人民法院关于审理利用信息网络侵害人身权益民事纠纷案件适用法律若干问题的规定》等规定，投诉通知须包含以下内容：</p>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        <h3 id=\"11-权利人主体信息和相关证明材料\">1.1 权利人主体信息和相关证明材料</h3>\n" +
                    "\n" +
                    "        <p><strong>（1）个人投诉：</strong> 权利人的真实姓名、有效联系方式、地址、身份证（中国公民）或护照（外国公民）等能够证明权利人主体身份的信息及材料。 <br>\n" +
                    "            <strong>（2）单位投诉：</strong> 权利人的名称、有效联系方式、住所地、投诉通知（加盖公章）、营业执照（加盖公章）等能够证明权利人主体身份的信息及材料。 <br>\n" +
                    "            <strong>（3）代他人投诉：</strong> 除上述（1）（2）中要求的投诉人和权利人双方的主体身份信息和证明材料外，还需提供权利人签名或盖章的委托书。</p>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        <h3 id=\"12-投诉内容\">1.2 投诉内容</h3>\n" +
                    "\n" +
                    "        <p><strong>（1）被投诉信息的完整标题/名称和网络地址等足以准确定位侵权内容的相关信息。</strong> <br>\n" +
                    "            若投诉对象为作品中的某一或几张图片/音频/视频/gif图等内容，还需说明：图片/音频/视频/gif图等内容的名称（若有）、标题（若有）或在文章中的具体位置，或者其他能准确定位图片的描述性文字。 <br>\n" +
                    "            <strong>（2）被投诉信息侵犯的权利类型。</strong> <br>\n" +
                    "            若权利人认为被投诉信息侵犯了其作品的信息网络传播权或其个人的人身权益等，需明确陈述被侵犯的权利类型。</p>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        <h3 id=\"13-构成侵权的初步证明材料\">1.3 构成侵权的初步证明材料</h3>\n" +
                    "\n" +
                    "        <p><strong>（1）著作权侵权投诉</strong>  <br>\n" +
                    "            <strong>A.著作权权属证明材料：</strong> 包括但不限于有权机构颁发的版权证书、商标权证书、专利权证书、作品首次公开发表或发行日期证明材料、创作手稿、经权威机构签发的作品创作时间戳、作品备案证书等能证明权利人拥有相关权利的有效权属证明。 <br>\n" +
                    "            <strong>B. 被投诉信息构成侵权的证明材料：</strong> 包括但不限于被投诉信息构成对权利人版权、商标权或专利权等权利侵权的有效证明材料。 <br>\n" +
                    "            <strong>（2）人身权侵权投诉</strong>  <br>\n" +
                    "            <strong>A. 指明被投诉信息中具体哪些内容涉嫌侵犯权利人的人身权益。</strong>  <br>\n" +
                    "            <strong>B. 被投诉信息对权利人人身权益构成侵权的事实和详细理由。</strong>  <br>\n" +
                    "            例如：若权利人认为被投诉信息与事实不符，须具体指出被投诉信息中哪些内容与事实不符，涉嫌侵犯权利人的何种权益，并详细说明真实情况作为投诉的事实与理由依据。    </p>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        <h2 id=\"14-权利人保证\">1.4 权利人保证</h2>\n" +
                    "\n" +
                    "        <p>权利人应在通知书中保证：权利人在通知书中的陈述和所提供材料皆是真实、有效和合法的，保证承担今日头条因根据权利人通知书对被投诉信息进行处理而遭受的任何损失。</p>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        <h2 id=\"2对合法投诉通知的处理\">2、对合法投诉通知的处理</h2>\n" +
                    "\n" +
                    "        <p>2.1 今日头条收到权利人符合本指引要求的投诉通知后，会尽快依法做删除或断开链接等处理。 <br>\n" +
                    "            2.2  对不符合本指引要求的投诉通知，今日头条有权在权利人补正前暂不处理。 <br>\n" +
                    "            2.3 对于侵犯权利人信息网络传播权的投诉通知，今日头条在进行处理投诉的同时可依法将投诉通知转送服务对象。若服务对象依法提交了反通知，今日头条会将反通知转送权利人。若权利人对反通知持有异议，依据法律规定，权利人应当且能够通过行政或司法程序直接和服务对象处理相关争议。</p>\n" +
                    "\n" +
                    "        <h1 id=\"二注意事项\">二、注意事项</h1>\n" +
                    "\n" +
                    "        <p>1、权利人应提供经签章或签名的书面通知书及相关证明材料的扫描件。为确保权利人提供相关材料的真实性和有效性，今日头条认为必要时，可要求您上述材料的原件，届时还请配合提供。 <br>\n" +
                    "            2、若权利人确有理由不能提供证明材料原件的，应提供其复印件（复印件上应有权利人签章）。 <br>\n" +
                    "            3、若证明材料是在外国或港澳台地区形成的，应按照法律规定在所在国家或地区进行公证认证或其他法律要求的证明程序。 <br>\n" +
                    "            4、 若权利人已经对被投诉人提起行政或司法争议解决程序的，请将通知书和相关受理证明、提交给争议解决机构的证据一并提交今日头条，这将有利于对投诉的处理。</p>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        <h1 id=\"三通知方式\">三、通知方式</h1>\n" +
                    "\n" +
                    "        <p>1、 权利人应按以下方式向今日头条送达投诉通知书： <br>\n" +
                    "            投诉受理邮箱：<a href=\"mailto:jubao@toutiao.com\">jubao@toutiao.com</a> <br>\n" +
                    "            2、 若权利人在今日头条平台上有自己的头条号，也可登录<a href=\"https://mp.toutiao.com\" target=\"_blank\">https://mp.toutiao.com</a>，通过页面底端“侵权投诉”进入“我要投诉”，在页面底端进行线上投诉。</p>\n");
            session.save(media);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }
    private void save2emdia4(){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Media media = new Media();
            media.setConent("<p>\n" +
                    "            《今日头条》支持蜘蛛协议（Robots Exclusion Protocol）“ToutiaoSpider”，同时，我们尊重所有的网络媒体，如媒体不希望内容被《今日头条》推荐，请及时邮件至\n" +
                    "\n" +
                    "            <a href=\"mailto:bd@toutiao.com\">bd@toutiao.com</a>\n" +
                    "\n" +
                    "            邮箱，或在网站页面中根据拒绝蜘蛛协议（Robots Exclusion Protocol）加注拒绝收录的标记，我们将对有异议的内容采取断开链接的做法。\n" +
                    "        </p>\n" +
                    "        <p></p>\n" +
                    "        <h2>no-transform协议</h2>\n" +
                    "        <p>转码支持的no-transform协议为如下两种形式：</p>\n" +
                    "        <p>1、HTTP Response中显示声明 Cache-control为no-transform；</p>\n" +
                    "        <p>2、meta标签中显示声明Cache-control为no-transform,格式为：</p>\n" +
                    "        <p><img src=\"http://p2.pstatp.com/large/1013/6493117447\">\n" +
                    "        </p>\n" +
                    "        <p>如果第三方网站不希望页面被今日头条客户端转码，可在页面中添加此协议，当用户进入时，会直接跳转至原网页。</p>\n" +
                    "\n" +
                    "        <p></p>\n" +
                    "        <h2>预加载技术</h2>\n" +
                    "\n" +
                    "        <p>今日头条为了让用户获得更好的体验，使用预加载技术极致提升用户打开文章的速度，使用户进入文章时几乎不用等待，实现“秒开”体验。</p>\n" +
                    "\n" +
                    "        <p>所谓预加载，是指用户在打开页面前，会预先加载文章的html、css、javascript这几部分内容。一些浏览器厂商为提高网页访问速度也同样使用此技术。比如：搜狗高速浏览器，其宣称的“智能预取，速度革命”，就是如此。</p>\n" +
                    "\n" +
                    "        <p>预加载技术特点：</p>\n" +
                    "\n" +
                    "        <p>1. 预加载只加载文本代码（html、css和javascript），不预加载图片。</p>\n" +
                    "        <p>2. 预加载不执行代码（javascript），不影响下游网站的流量统计。</p>\n" +
                    "        <p>3. 广告不进行预加载。 </p>\n");
            session.save(media);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Test
    public void getBestComment(){
        Session session = null;
        Transaction tx = null;
        List<Object[]> numbers = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            //取数据
            numbers = session.createQuery("select cl.myComment.comment_id, count(cl.myComment.comment_id)" +
                    "from CommentLike cl " +
                    "group by cl.myComment.comment_id order by count(cl.myComment.comment_id) desc")
                    .setMaxResults(3)
                    .list();
            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }
        List<Object[]> commentRelation = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            /*System.out.print(numbers.get(i)[0]+":::::");
            System.out.println(numbers.get(i)[1]);
            System.out.println("-----------------------------");*/

            List<Object[]> commentTemp = new ArrayList<>();

            try{
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();
                //取数据
                commentTemp = session.createQuery("select a.article_id, a.title, u.name,  c.content,  a.author " +
                        "from MyUser u, MyArticle a, MyComment c " +
                        "where c.myUser.id = u.user_id and c.myArticle.article_id = a.article_id and c.comment_id = :commentId")
                        .setInteger("commentId",Integer.parseInt(numbers.get(i)[0].toString()))
                        .list();
                tx.commit();
                commentTemp.get(0)[4] = numbers.get(i)[1];
            }catch (HibernateException e) {
                if(tx!=null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            }finally{
                HibernateUtil.closeSession();
            }
            commentRelation.addAll(commentTemp);
        }
        for (Object[] objects:commentRelation){
            System.out.println(objects[0].toString());
            System.out.println(objects[1].toString());
            System.out.println(objects[2].toString());
            System.out.println(objects[3].toString());
            System.out.println(objects[4].toString());
            System.out.println("---------------------------------");
        }

    }

    @Test
    public void findByPage(){
        int pageNo = 0;
        int pageSize = 15;
        Session session = null;
        Transaction tx = null;
        List<Object[]> myArticles;

        List<Object[]> articles = new ArrayList<>();

        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            MyUser myUser = session.get(MyUser.class,1);
            //取数据
            myArticles = session.createQuery("select a.article_id, a.title,a.abstracts, a.article_pic,a.author,a.pub_date from MyArticle a order by article_id desc ")
                    .setFirstResult(pageNo*pageSize)
                    .setMaxResults(pageSize)
                    .list();

            for (int i = 0; i < 15; i++) {
                Object[] objects = new Object[13];
                Object obj0 = session.createQuery("select count(c.myArticle.article_id) " +
                        "from MyComment c " +
                        "where c.myArticle.article_id =:article_id " +
                        "group by c.myArticle.article_id ")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                Object obj1 = session.createQuery("select count(a.myArticle.article_id) from ArticleLike a " +
                        "where a.myArticle.article_id = :article_id " +
                        "group by a.myArticle.article_id")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                Object obj2 = session.createQuery("select count(a.myArticle.article_id) from ArticleDisLike a " +
                        "where a.myArticle.article_id = :article_id " +
                        "group by a.myArticle.article_id")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();
                Object obj3 = session.createQuery("select count(a.myArticle.article_id) from MyCollocation a " +
                        "where a.myArticle.article_id = :article_id " +
                        "group by a.myArticle.article_id")
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .uniqueResult();

                List<Object> obj4 = session.createQuery("from ArticleLike a " +
                        "where a.myUser.user_id = :userId " +
                        "and a.myArticle.article_id = :article_id")
                        .setInteger("userId",1)
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .list();

                List<Object> obj5 = session.createQuery("from ArticleDisLike a " +
                        "where a.myUser.user_id = :userId " +
                        "and a.myArticle.article_id = :article_id")
                        .setInteger("userId",1)
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .list();

                List<Object> obj6 = session.createQuery("from MyCollocation a " +
                        "where a.myUser.user_id = :userId " +
                        "and a.myArticle.article_id = :article_id")
                        .setInteger("userId",1)
                        .setInteger("article_id",Integer.parseInt(myArticles.get(i)[0].toString()))
                        .list();

                objects[0] = myArticles.get(i)[0];
                objects[1] = myArticles.get(i)[1];
                objects[2] = myArticles.get(i)[2];
                objects[3] = myArticles.get(i)[3];
                objects[4] = myArticles.get(i)[4];
                objects[5] = myArticles.get(i)[5];

                if (obj0 != null){
                    objects[6] = obj0;
                }else {
                    objects[6] = 0;
                }

                if (obj0 != null){
                    objects[6] = obj0;
                }else {
                    objects[6] = 0;
                }

                if (obj1 != null){
                    objects[7] = obj1;
                }else {
                    objects[7] = 0;
                }

                if (obj2 != null){
                    objects[8] = obj2;
                }else {
                    objects[8] = 0;
                }

                if (obj3 != null){
                    objects[9] = obj3;
                }else {
                    objects[9] = 0;
                }
                if (obj4 != null && obj4.size() != 0){
                    objects[10] = 1;
                }else {
                    objects[10] = 0;
                }
                if (obj5 != null && obj5.size() != 0){
                    objects[11] = 1;
                }else {
                    objects[11] = 0;
                }
                if (obj6 != null && obj6.size() != 0){
                    objects[12] = 1;
                }else {
                    objects[12] = 0;
                }

                articles.add(objects);
            /*    if (obj0 != null)
                    System.out.println(obj0.toString());
                if (obj1 != null)
                    System.out.println(obj1.toString());
                if (obj2 != null)
                    System.out.println(obj2.toString());
                if (obj3 != null)
                    System.out.println(obj3.toString());
                System.out.println("-----------------------------");*/

            }
            tx.commit();
        }catch (HibernateException e) {
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            HibernateUtil.closeSession();
        }

        for (Object[] objs:articles){
            System.out.println("0::"+objs[0].toString());
            System.out.println("1::"+objs[1].toString());
            System.out.println("2::"+objs[2].toString());
            System.out.println("3::"+objs[3].toString());
            System.out.println("4::"+objs[4].toString());
            System.out.println("5::"+objs[5].toString());
            System.out.println("6::"+objs[6].toString());
            System.out.println("7::"+objs[7].toString());
            System.out.println("8::"+objs[8].toString());
            System.out.println("9::"+objs[9].toString());
            System.out.println("10::"+objs[10].toString());
            System.out.println("11::"+objs[11].toString());
            System.out.println("12::"+objs[12].toString());
            System.out.println("----------------------------------------");
        }
    }

    @Test
    public void save2articlelikes() {
        for (int i = 1; i < 100; i++){
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSession();
                tx = session.beginTransaction();

                ArticleLike articlelike = new ArticleLike();
                articlelike.setMyUser(session.get(MyUser.class, 1));
                articlelike.setMyArticle(session.get(MyArticle.class, 1060-i));

                session.save(articlelike);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    public void getComments(int pageNo, int pageSize){
        int article_id = 6;
        Session session = null;
        Transaction tx = null;
        List<Object[]> commentRela = new ArrayList<>();

        List<Object[]> comments = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            comments = session.createQuery("select u.user_id, c.comment_id, c.content, c.coment_date, u.name " +
                    "from MyComment c, MyUser u " +
                    "where c.myArticle.article_id = :article_id " +
                    "and c.myUser.user_id = u.user_id")
                    .setInteger("article_id",article_id)
                    .setFirstResult(pageNo*pageSize)
                    .setMaxResults((pageNo+1)*pageSize)
                    .list();
            if(comments != null){
                for (int i = 0; i < comments.size() ; i++) {
                    Object[] objects = new Object[6];
                    Object object = session.createQuery("select count(cl.myComment.comment_id) " +
                            "from CommentLike cl " +
                            "where cl.myComment.comment_id = :comment_id " +
                            "group by cl.myComment.comment_id")
                            .setInteger("comment_id",Integer.parseInt(comments.get(i)[1].toString()))
                            .uniqueResult();

                    objects[0] = comments.get(i)[0];
                    objects[1] = comments.get(i)[1];
                    objects[2] = comments.get(i)[2];
                    objects[3] = comments.get(i)[3];
                    objects[4] = comments.get(i)[4];
                    if (object != null)
                        objects[5] = object;
                    else
                        objects[5] = 0;

                    commentRela.add(objects);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
        for (Object[] object:commentRela){
            System.out.println(object[0].toString());
            System.out.println(object[1].toString());
            System.out.println(object[2].toString());
            System.out.println(object[3].toString());
            System.out.println(object[4].toString());
            System.out.println(object[5].toString());
            System.out.println("-------------------------------");
        }
    }
}
