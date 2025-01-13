
public class OutputValidator {
    private final Output output;
   

    public OutputValidator(Output output) {
        this.output = output;
    }

    public boolean validate()
    {
        int[] levels=new int[3];
        boolean[] hasLock=new boolean[3];

        for (String line : output.getLines())
        {
            String[] parts=line.split(" ");
            String threadName=parts[0];
            String action=parts[2];
            int threadId=Integer.parseInt(threadName.substring(threadName.length()-1)) %3;

            if (action.equals("at"))
            {
                int level=Integer.parseInt(parts[3].substring(1));
                if (levels[threadId]!= level-1)
                {
                    return false;
                }
                levels[threadId]=level;
            }
            else if (action.equals("is"))
            {
                int level=Integer.parseInt(parts[5].substring(1));
                if (levels[threadId]!= level)
                {
                    return false;
                }
            }
            else if (action.equals("has"))
            {
                if (hasLock[threadId])
                {
                    return false;
                }
                hasLock[threadId]=true;
            }
            else if (action.equals("unlocked"))
            {
                if (!hasLock[threadId])
                {
                    return false;
                }
                hasLock[threadId]=false;
                levels[threadId]=0;
            }
        }
        return true;
        
    }

    
}