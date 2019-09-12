//稳定匹配
import java.util.ArrayList;
import java.util.LinkedList;


class People {
    private String name;
    protected int ID = 0;

    public People(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Man extends People {
    private int[] prefers;
    private int index;

    public Man(String name,int ID) {
        super(name, ID);
    }

    public Man setPrefers(Woman... women) {
        prefers = new int[women.length];
        for (int i = 0; i < women.length; i++) {
            prefers[i] = women[i].getID();
        }
        index = 0;
        return this;
    }

    public int next() {
        if (index < prefers.length) {
            return prefers[index++];
        } else {
            return -1;
        }
    }
}

class Woman extends People {
    private int[] prefers;

    public Woman(String name, int ID) {
        super(name,ID);
    }

    public Woman setPrefers(Man... men) {
        prefers = new int[men.length];
        for (int i = 0; i < men.length; i++) {
            //数组内容为0的是最喜欢的男人
            prefers[men[i].getID()] = i;
        }
        return this;
    }

    //判断之前分配给女人的男人好还是现在的这个男人好,若现在的好则为真
    public boolean prefer(int cur, int next) {
        return prefers[next] < prefers[cur];
    }
}

public class Lab1{
    public static void main(String[] args) {
        ArrayList<Man> menList;//一维数组，存储每个男人，对象男人内又包含喜欢的女人ID序列
        ArrayList<Woman> womenList;//一维数组，存储每个女人，对象女人中又包含喜欢的男人ID序列
        LinkedList<Man> menTemp;//链表，存储未匹配的男人
        Integer[] current;//存储正确的配对，下标为女人ID，内容为男人ID
        int ID = 0;
        //int count = 0;
        Man lvbu = new Man("吕布", ID++);
        Man liubei = new Man("刘备", ID++);
        Man kongming = new Man("孔明", ID++);
        Man zhouyu = new Man("周瑜", ID++);
        Man caocao = new Man("曹操", ID++);

        ID=0;
        Woman diaochan = new Woman("貂蝉", ID++);
        Woman daqiao = new Woman("大乔", ID++);
        Woman xiaoqiao = new Woman("小乔", ID++);
        Woman shangxiang = new Woman("尚香", ID++);
        Woman achou = new Woman("阿丑", ID++);

        menList = new ArrayList<>();
        menList.add(lvbu.setPrefers(diaochan,daqiao,xiaoqiao,achou,shangxiang));
        menList.add(liubei.setPrefers(diaochan,xiaoqiao,daqiao,shangxiang,achou));
        menList.add(kongming.setPrefers(achou,diaochan,xiaoqiao,daqiao,shangxiang));
        menList.add(zhouyu.setPrefers(xiaoqiao,daqiao,shangxiang,diaochan,achou));
        menList.add(caocao.setPrefers(xiaoqiao,diaochan,daqiao,shangxiang,achou));

        womenList = new ArrayList<>();
        womenList.add(diaochan.setPrefers(caocao,lvbu,liubei,zhouyu,kongming));
        womenList.add(daqiao.setPrefers(zhouyu,liubei,kongming,lvbu,caocao));
        womenList.add(xiaoqiao.setPrefers(zhouyu,kongming,liubei,caocao,lvbu));
        womenList.add(shangxiang.setPrefers(lvbu,liubei,zhouyu,kongming,caocao));
        womenList.add(achou.setPrefers(kongming,zhouyu,caocao,liubei,lvbu));
        
        menTemp = new LinkedList<>();
        for (Man m : menList) {
            menTemp.push(m);
        }
        current = new Integer[menList.size()];
        for (int i = 0; i < menList.size(); i++) {
            current[i] = null;
        }
        while (!menTemp.isEmpty()) {
            Man man = menTemp.pop();
            int prefer = man.next();//返回当前男人最喜欢的女人的ID
            if (prefer != -1) {
                if (current[prefer] == null) {
                    current[prefer] = man.getID();//女人ID为下标，男人ID为数组内容
                } else {
                    //判断条件：若已经分配给女人一个男人，
                    //则判断之前分配给女人的男人好还是现在的这个男人好
                    //若现在的好则为真
                    if (womenList.get(prefer).prefer(current[prefer], man.getID())) {
                        menTemp.push(menList.get(current[prefer]));
                        current[prefer] = man.getID();
                    } else {
                        menTemp.push(man);
                    }
                }
            }
            //count++;
        }
        //System.out.println("count:"+count);
        for (int i = 0; i < current.length; i++) {
            System.out.print(womenList.get(i));
            System.out.print("-");
            System.out.println(menList.get(current[i]));
        }
    }
}