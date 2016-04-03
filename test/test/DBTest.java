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
