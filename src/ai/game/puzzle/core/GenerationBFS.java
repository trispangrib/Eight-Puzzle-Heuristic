package ai.game.puzzle.core;

import java.util.ArrayList;

public class GenerationBFS
{
    private String target;
    private String data;
    private String tempResult;
    private ArrayList<OffSpring> openList;
    private ArrayList<OffSpring> closeList;
    private ManipulasiString manipulasiString;
    private OffSpring offSpring;
    private OffSpring gOS;
    private int parent;
    private int prevParent;
    private String parentMovement;
    private int currentId;
    private int level;
    private String route;
    private int cntChild;

    public GenerationBFS(String target, String data) 
    {
        this.tempResult = "";
        this.target = target;
        this.data = data;
        openList = new ArrayList<>();
        closeList = new ArrayList<>();
        prevParent = 0;
        route = "";
    }

    public ArrayList<OffSpring> getOpenList() {
        return openList;
    }
    
    private int addCurrentId()
    {
        this.currentId += 1;
        return this.currentId;
    }
    
    private void addLevel()
    {
        this.level += 1;
    }
    
    private int defineLevel(int parent)
    {
        int countLevel=1;
        int curParent = parent;
        
        for(OffSpring s : closeList)
        {
            if(s.getId()==curParent && curParent != 0)
            {
                countLevel++;
                curParent = s.getParent();
            }
        }
        
        return countLevel;
    }

    public int getParent() {
        return this.parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
    
    private void printOpenList()
    {
        System.out.println("Hasil OpenList : ");
        for(OffSpring s : openList)
        {
            System.out.println(s.getData()+" - "+s.getId()+"-"+s.getMovement()+"-"+s.getParent()+"-"+s.getLevel()+"-"+s.getHeuristicValue());
        }
    }
    
    public void printCloseList()
    {
        System.out.println("Hasil CloseList: ");
        for(OffSpring s : closeList)
        {
            System.out.println(s.getData()+" - "+s.getId()+"-"+s.getMovement()+"-"+s.getParent()+"-"+s.getLevel()+"-"+s.getHeuristicValue());
        }
    }
    
    public int getHeuristic(String data){
    return 0;
    }
    
    public boolean matchTarget()
    {
        for(OffSpring s : openList)
        {
            if(s.getData().matches(this.target))
            {
                this.gOS = s;
                return true;
            }
        }
        return false;
    }
    
    public void createChildren()
    {
        int cnt = 0;
        while(this.level<20)
        {
            createLeftChild();
            createRightChild();
            createUpChild();
            createBotChild();
            
            if(this.matchTarget())
            {
                break;
            }
            else
            {
                this.data = openList.get(0).getData();
                this.setParent(openList.get(0).getId());
                this.closeList.add(openList.get(0));
                this.openList.remove(0);
            }
            cnt++;
        }
        
        this.printOpenList();
        this.printCloseList();
        this.setRoute();
        this.displayRoute();
        this.displayRouteBackward();
        System.out.println("Total semua iterasi hingga menemukan solusi/final state = "+cnt);
        this.displayCountChildren();
    }

    public void createRightChild()
    {
        String temp[]=null;
        manipulasiString = new ManipulasiString(this.data);
        if(manipulasiString.getRightSide() != null && !this.isCreated(manipulasiString.getRightSide()))
        {
            temp = this.manipulasiString.getData();
            temp[this.manipulasiString.getPosition()] =
            manipulasiString.getRightSide();
            temp[this.manipulasiString.getPosition()+1] = "0";

            for(int i=0; i < temp.length; i++) 
            {
                tempResult = tempResult.concat(temp[i]);
                if(i+1 < temp.length)
                {
                    tempResult = tempResult.concat(",");
                }
            }
            this.offSpring = new OffSpring(this.tempResult,"Right",this.getParent(),this.addCurrentId(),getHeuristic(this.tempResult),this.defineLevel(this.getParent()));
            openList.add(this.offSpring);
            this.cntChild++;
            this.tempResult = "";
        }
    }
    
    public void createLeftChild()
    {
        String temp[]=null;
        manipulasiString = new ManipulasiString(this.data);
        if(manipulasiString.getLeftSide() != null && !this.isCreated(manipulasiString.getLeftSide()))
        {
            temp = this.manipulasiString.getData();
            temp[this.manipulasiString.getPosition()] = manipulasiString.getLeftSide();
            temp[this.manipulasiString.getPosition()-1] = "0";

            for (int i = 0; i < temp.length; i++) 
            {
                tempResult = tempResult.concat(temp[i]);
                if(i+1 < temp.length)
                {
                    tempResult = tempResult.concat(",");
                }
            }
            this.offSpring = new OffSpring(this.tempResult,"Left",this.getParent(),this.addCurrentId(),getHeuristic(this.tempResult),this.defineLevel(this.getParent()));
            openList.add(this.offSpring);
            this.cntChild++;
            this.tempResult = "";
            }
    }

    public void createBotChild()
    {
        String temp[]=null;
        manipulasiString = new ManipulasiString(this.data);
        if(manipulasiString.getBotSide() != null && !this.isCreated(manipulasiString.getBotSide()))
        {
            temp = this.manipulasiString.getData();
            temp[this.manipulasiString.getPosition()] = manipulasiString.getBotSide();
            temp[this.manipulasiString.getPosition() + (int)Math.sqrt(temp.length)] = "0";

            for (int i = 0; i < temp.length; i++) 
            {
                tempResult = tempResult.concat(temp[i]);
                if(i+1 < temp.length)
                {
                    tempResult = tempResult.concat(",");
                }
            }
            this.offSpring = new OffSpring(this.tempResult,"Bottom",this.getParent(),this.addCurrentId(),getHeuristic(this.tempResult),this.defineLevel(this.getParent()));
            openList.add(this.offSpring);
            this.cntChild++;
            this.tempResult = "";
        }
    }
    
    public void createUpChild()
    {
        String temp[]=null;
        manipulasiString = new ManipulasiString(this.data);
        if(manipulasiString.getUpSide() != null && !this.isCreated(manipulasiString.getUpSide()))
        {
            temp = this.manipulasiString.getData();
            temp[this.manipulasiString.getPosition()] = manipulasiString.getUpSide();
            temp[this.manipulasiString.getPosition() - (int)Math.sqrt(temp.length)] = "0";

            for (int i = 0; i < temp.length; i++) 
            {
                tempResult = tempResult.concat(temp[i]);
                if(i+1 < temp.length)
                tempResult = tempResult.concat(",");
            }
            this.offSpring = new OffSpring(this.tempResult,"Up",this.getParent(),this.addCurrentId(),getHeuristic(this.tempResult),this.defineLevel(this.getParent()));
            openList.add(this.offSpring);
            this.cntChild++;
            this.tempResult = "";
        }
    }

    public boolean isCreated(String os)
    {
        for(OffSpring s:openList) 
        {
            if(s.getData().matches(os))
            {
                return true;
            }
        }
        for(OffSpring s:closeList) 
        {
            if(s.getData().matches(os))
            {
                return true;
            }
        }
        return false;
    }
    
    public void displayRoute()
    {
        System.out.println("Route : ");
        for(int i=0; i<route.length(); i++)
        {
            System.out.println(route.charAt(i));
        }
    }
    
    public void setRoute()
    {       
        String temp = "";
        int curId = gOS.getId();
        temp += gOS.getMovement().charAt(0);
        
        curId = gOS.getParent();
        while(curId != 0)
        {
            for(OffSpring s : closeList)
            {
                if(s.getId()==curId)
                {
                    temp += s.getMovement().charAt(0);
                    curId = s.getParent();
                }
            }
        }
        
        for(int i=temp.length()-1; i>=0; i--)
        {
            route += temp.charAt(i);
        }
    }
     
    public void displayRouteBackward()
    {
        System.out.println("String data from final state to inisial state / root:");
        int curId = gOS.getId();
        System.out.println(gOS.getId()+"-"+gOS.getMovement()+"-"+gOS.getParent()+"-"+gOS.getLevel()+"-"+gOS.getData());
           
        curId = gOS.getParent();
        while(curId != 0)
        {
            for(OffSpring s : closeList)
            {
                if(s.getId()==curId)
                {
                    System.out.println(s.getId()+"-"+s.getMovement()+"-"+s.getParent()+"-"+s.getLevel()+"-"+s.getData());
                    curId = s.getParent();
                }
            }
        }
    }
    
    public void displayCountChildren()
    {
        System.out.println("Total seluruh anak sampai iterasi ditemukan = "+this.cntChild);
    }   
}