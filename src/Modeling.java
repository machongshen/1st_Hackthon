

import java.util.List;

import Utils.Company;
import Utils.Price;

public class Modeling {

		
	public void calculateHistory(int i, List<Company> companylist)
	{
			List<Price> price = companylist.get(i).getStock_price();
		
			
			float penultimateday = 0;
			float lastday  = (price.get(0).getStart_price() +price.get(0).getEnd_price())/2;
							
			int count = 0;
			int score = 0;
			
			
			for(int j = 1; j <price.size(); j++)
			{
				
				float today = (price.get(j).getStart_price() +price.get(j).getEnd_price())/2;
				
				//upwards 
				if(lastday < today && penultimateday <lastday)
				{
					penultimateday = lastday;
					lastday = today;
					
					count++;
					
					if(count <=3)
						score = score +1;
					else if(count >3 && count <= 7)
						score = score +2;
					else
						score = score +4;
					
					companylist.get(i).setUpwards(true);
	
				}
				
				// bottom
				else if(lastday < today && lastday < penultimateday)
				{
					penultimateday = lastday;
					lastday = today;
					
					count = 1;
					
					score = score +1;
					
					companylist.get(i).setUpwards(false);
				

				}
				//peak
				else if(lastday > today && lastday > penultimateday )
				{
					penultimateday = lastday;
					lastday = today;
					
					count = -1;
					score = score -1;
					
					companylist.get(i).setUpwards(true);
				}
				
				//downwards
				else 
				{
					penultimateday = lastday;
					lastday = today;
					
					count --;
					
					if(count >= -3)
						score = score -1;
					else if (count <-3 && count >=-7)
						score = score -2;
					else
						score = score -4;
					
					companylist.get(i).setUpwards(false);
				}			
					
			}	
			
			companylist.get(i).setScore1(score);

			
		}
		
	
	

	public void calculateMA(int index,List<Company> companylist) {
		

		List<Price> price = companylist.get(index).getStock_price();
		float lastdayprice = (price.get(price.size()-1).getStart_price() + price.get(price.size()-1).getEnd_price())/2;
		
		List<Float> MA5 = companylist.get(index).getMA5();
		float lastdayMA5 = MA5.get(MA5.size()-1);
	
		List<Float> MA10 = companylist.get(index).getMA10();
		
		float lastdayMA10 = MA10.get(MA10.size()-1);
		
		boolean upwards = companylist.get(index).isUpwards();
		
		int score = 0;
		
		
		if(lastdayprice > lastdayMA5 && upwards)
			score = score + 5;
		
		else if (lastdayprice > lastdayMA5 && !upwards)
			score = score -2;
		
		else if(lastdayprice <lastdayMA5 && upwards)
			score = score + 2;
		
		else if(lastdayprice < lastdayMA5 && !upwards )
			score = score -5;
		
		
		if(lastdayprice > lastdayMA10 && upwards)
			score = score + 10;
		
		else if (lastdayprice > lastdayMA10 && !upwards)
			score = score -5;
		
		else if(lastdayprice <lastdayMA10 && upwards)
			score = score + 5;
		
		else if(lastdayprice < lastdayMA10 && !upwards )
			score = score - 10;
		
		
		companylist.get(index).setScore2(score);
		
		
	}

	public void RiskControl(int index,List<Company> companylist){
		
		float historylow = companylist.get(index).getHislow();
		float historyhigh = companylist.get(index).getHishigh();
		
		List<Price> price = companylist.get(index).getStock_price();
		float lastdayprice = (price.get(price.size()-1).getStart_price() + price.get(price.size()-1).getEnd_price())/2;
		
		if( Math.abs(lastdayprice -historylow) <=0.5 )
			//companylist.get(index).setScore3(10);
			companylist.get(index).setScore3(0);
		else if ( Math.abs(lastdayprice -historyhigh) <=0.5 )
			//companylist.get(index).setScore3(-10);
			companylist.get(index).setScore3(1);
		else
			companylist.get(index).setScore3(2);
		
	}
	
	public void Grading(int index, List<Company> companylist)
	{
		int score1 = companylist.get(index).getScore1();
		int score2 = companylist.get(index).getScore2();
		int score3 = companylist.get(index).getScore3();
		
		int total = score1 + score2;
		int grade = 3;
		
		if(total > 35 && score3 == 1)
			grade = 2;
		else if (total >35 && score3 ==2)
			grade = 4;
		else if(total>= 10 && total <=35 && score3 ==1)
			grade =3;
		else if(total>= 10 && total <=35 && score3 ==2)
			grade = 4;
		else if(total >=0 && total <10)
			grade =3;
		else if(total< -10 && grade  == 0)
			grade =5;
		else if(total <-10 && grade == 2)
			grade = 1;
		else if(total< 0 && total >=-10 && grade  == 2)
			grade =2;
		else if(total< 0 && total >=-10 && grade  == 0)
			grade =4;
		
		companylist.get(index).setGrade(grade);
		
		
	}
	
}




