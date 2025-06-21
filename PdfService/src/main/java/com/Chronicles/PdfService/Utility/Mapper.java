package com.Chronicles.PdfService.Utility;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Mapper {
    public Map<String, String> branchMapper = new HashMap<>();
    public  Map<String, String> collegeMapper = new HashMap<>();
    public Set<String> priorityMapper=new HashSet<>();
    public Mapper(){
        branchMapper.put("CSAI", "Computer Science and Engineering (Artificial Intelligence)");
        branchMapper.put("EIOT", "Electronics and Communication Engineering (Internet of Things)");
        branchMapper.put("CSDA", "Computer Science and Engineering (Big Data Analytics)");
        branchMapper.put("CSE", "Computer Science and Engineering");
        branchMapper.put("ECE", "Electronics and Communication Engineering");
        branchMapper.put("CIOT", "Computer Science and Engineering (Internet of Things)");
        branchMapper.put("CSDS", "Computer Science and Engineering (Data Science)");
        branchMapper.put("EE", "Electrical Engineering");
        branchMapper.put("ECAM", "Electronics and Communication Engineering (Artificial Intelligence and Machine Learning)");
        branchMapper.put("IT", "Information Technology");
        branchMapper.put("ICE", "Instrumentation and Control Engineering");
        branchMapper.put("MEEV", "Mechanical Engineering (Electric Vehicles)");
        branchMapper.put("ITNS", "Information Technology (Network and Information Security)");
        branchMapper.put("ME", "Mechanical Engineering");
        branchMapper.put("CE", "Civil Engineering");
        branchMapper.put("MAC", "Mathematics and Computing");
        branchMapper.put("BT", "Bio-Technology");
        branchMapper.put("GI", "Geoinformatics");
        branchMapper.put("MAE", "Mechanical and Automation Engineering");
        branchMapper.put("TE", "Tool Engineering");
        branchMapper.put("MEE", "Mechatronics Engineering");
        branchMapper.put("CS", "Computer Science & Engineering");
        branchMapper.put("CD", "Computer Science & Engineering (Data Science)");
        branchMapper.put("IS", "Information Science & Engineering");
        branchMapper.put("CY", "Computer Science & Engineering (Cyber Security)");
        branchMapper.put("AI", "Artificial Intelligence & Machine Learning");
        branchMapper.put("EC", "Electronics & Communication Engineering");
        branchMapper.put("CI", "Computer Science & Engineering (Artificial Intelligence & Machine Learning)");
        branchMapper.put("IC", "Computer Science & Engineering (IOT & Cyber Security Including Blockchain Technology)");
        branchMapper.put("CB", "Computer Science & Business Systems");
        branchMapper.put("EEE", "Electrical & Electronics Engineering");
        branchMapper.put("EI", "Electronics & Instrumentation Engineering");
        branchMapper.put("CG", "Computer Science & Design");
        branchMapper.put("AS", "Aerospace Engineering");
        branchMapper.put("RI", "Robotics & Artificial Intelligence");
        branchMapper.put("AD", "Artificial Intelligence & Data Science");
        branchMapper.put("ET", "Electronics & Telecommunication Engineering");
        branchMapper.put("CH", "Chemical Engineering");
        branchMapper.put("CV", "Civil Engineering");
        branchMapper.put("CA", "Computer Science & Engineering (Artificial Intelligence)");
        branchMapper.put("CSI", "Computer Science & Engineering (IOT)");
        branchMapper.put("IM", "Industrial Engineering & Management");
        branchMapper.put("AE", "Aeronautical Engineering");
        branchMapper.put("VD", "Electronics Engineering (VLSI Design & Technology)");
        branchMapper.put("EIE", "Electronics & Instrumentation Engineering (Instrumentation Technology)");
        branchMapper.put("RA", "Robotics & Automation");
        branchMapper.put("MD", "Medical Electronics Engineering");
        branchMapper.put("INT", "Information Technology");
        branchMapper.put("CST", "Computer Science & Technology");
        branchMapper.put("CIT", "Computer Science & Information Technology");
        branchMapper.put("AM", "Automation & Robotics Engineering");
        branchMapper.put("CNW", "Computer Science & Engineering (Networks)");
        branchMapper.put("CBD", "Computer Science & Technology (Big Data)");
        branchMapper.put("CSD", "Computer Science & Technology (DevOps)");
        branchMapper.put("IST", "Information Science & Technology");
        branchMapper.put("AVE", "Automotive Engineering");
        branchMapper.put("CSB", "Computer Science & Engineering (Blockchain)");
        branchMapper.put("AIML", "Artificial Intelligence and Machine Learning");
        branchMapper.put("AIDS", "Artificial Intelligence and Data Science");
        branchMapper.put("ITE", "Information Technology Engineering");
        branchMapper.put("ANR", "Automation and Robotics Engineering");
        branchMapper.put("IIOT", "Industrial Internet of Things");
        branchMapper.put("ITOT", "Information Technology and Operational Technology");
        branchMapper.put("BCE", "Biotechnology and Chemical Engineering");
        branchMapper.put("CSE (Cyber)", "Computer Science and Engineering (Cyber Security)");
        branchMapper.put("CIVIL", "Civil Engineering");


        collegeMapper.put("NSUT", "Netaji Subhas University of Technology");
        collegeMapper.put("DTU", "Delhi Technological University");
        collegeMapper.put("IIITD", "Indraprastha Institute of Information Technology, Delhi");
        collegeMapper.put("IGDTUW", "Indira Gandhi Delhi Technical University for Women");
        collegeMapper.put("DSEU", "Delhi Skill and Entrepreneurship University");



// Top Colleges WBJEE
        priorityMapper.add("Jadavpur University (JU)");
        priorityMapper.add("Maulana Abul Kalam Azad University of Technology (MAKAUT)");
        priorityMapper.add("University of Calcutta (UC)");
        priorityMapper.add("Kalyani University");
        priorityMapper.add("Kalyani Government Engineering College");
        priorityMapper.add("Institute of Engineering and Management (IEM)");
        priorityMapper.add("Heritage Institute of Technology");
        priorityMapper.add("JIS College of Engineering");
        priorityMapper.add("Haldia Institute of Technology (HIT)");
        priorityMapper.add("Narula Institute of Technology (NIT)");

// Top Colleges COMED K
        priorityMapper.add("M.S. Ramaiah Institute of Technology (MSRIT), Bangalore");
        priorityMapper.add("R.V. College of Engineering (RVCE), Bangalore");
        priorityMapper.add("BMS College of Engineering, Bangalore");
        priorityMapper.add("Siddaganga Institute of Technology (SIT), Tumkur");
        priorityMapper.add("NMAM Institute of Technology, Nitte");
        priorityMapper.add("CMR Institute of Technology, Bangalore");
        priorityMapper.add("Bangalore Institute of Technology (BIT), Bangalore");
        priorityMapper.add("Dayananda Sagar College of Engineering (DSCE), Bangalore");
        priorityMapper.add("KLE Technological University, Hubli");
        priorityMapper.add("JSS Science and Technology University, Mysore");

// Top Colleges MHT-CET
        priorityMapper.add("COEP Technological University");
        priorityMapper.add("Pune Institute of Computer Technology, Dhankavdi, Pune");
        priorityMapper.add("Bhartiya Vidya Bhavan's Sardar Patel Institute of Technology, Andheri, Mumbai");
        priorityMapper.add("Shri Vile Parle Kelvani Mandal's Dwarkadas J. Sanghvi College of Engineering, Vile Parle, Mumbai");
        priorityMapper.add("Bansilal Ramnath Agarawal Charitable Trust's Vishwakarma Institute of Technology, Bibwewadi, Pune");
        priorityMapper.add("Thadomal Shahani Engineering College, Bandra, Mumbai");
        priorityMapper.add("Pimpri Chinchwad Education Trust, Pimpri Chinchwad College of Engineering, Pune");
        priorityMapper.add("K J Somaiya Institute of Technology");
        priorityMapper.add("Vivekanand Education Society's Institute of Technology, Chembur, Mumbai");
        priorityMapper.add("Thakur College of Engineering and Technology, Kandivali, Mumbai");

// Top Colleges GGSIPU
        priorityMapper.add("University School of Information Technology (USIT)");
        priorityMapper.add("Bharati Vidyapeeth's College of Engineering (BVCOE), Paschim Vihar");
        priorityMapper.add("Maharaja Agrasen Institute of Technology (MAIT), Rohini");
        priorityMapper.add("Maharaja Surajmal Institute of Technology (MSIT), Janakpuri");
        priorityMapper.add("Bhagwan Parshuram Institute of Technology (BPIT), Rohini");
        priorityMapper.add("Delhi Technical Campus (DTC), Greater Noida");

// Top Colleges UPTAC
        priorityMapper.add("Harcourt Butler Technical University (HBTU), Kanpur");
        priorityMapper.add("Institute of Engineering and Technology (IET), Lucknow");
        priorityMapper.add("KIET Group of Institutions, Ghaziabad");
        priorityMapper.add("ABES Engineering College, Ghaziabad");
        priorityMapper.add("Galgotias College of Engineering and Technology, Greater Noida");
        priorityMapper.add("JSS Academy of Technical Education (JSSATE), Noida");
        priorityMapper.add("Babu Banarasi Das University (BBDU), Lucknow");
        priorityMapper.add("G L Bajaj Institute of Technology and Management, Greater Noida");
        priorityMapper.add("Noida Institute of Engineering and Technology (NIET), Greater Noida");

    }

    public boolean isBranchAvailable(String branchCode)
    {
        return branchMapper.containsKey(branchCode);
    }
    public boolean isCollegeAvailable(String collegeCode)
    {
        return collegeMapper.containsKey(collegeCode);
    }
    public String getBranchName(String branchCode)
    {
        return branchMapper.get(branchCode);
    }
    public  String getCollegeName(String collegeCode)
    {
        return  collegeMapper.get(collegeCode);
    }

}
