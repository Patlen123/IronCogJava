package com.company;
import java.util.ArrayList;
import java.util.Scanner;

public class Node {
    private String strNodeName;
    private ArrayList<Node> nodNodes = new ArrayList<Node>;
    private String strDeepContents;
    private String strPrevString;
    private String strPrevString2;

    public Node(String nodeName, String contents)   {
        Scanner in = new Scanner(contents);
        strNodeName = nodeName;
        while (in.hasNext())    {

            if (in.next().equals("{")) {
                strDeepContents = "";
                while (!in.next().equals("}"))  {
                    strDeepContents = strDeepContents + " " +  in.next();
                }
                nodNodes.add(new Node())
            }
        }

    }

    public String metGetName()  {
        return strNodeName;
    }

    public
}
