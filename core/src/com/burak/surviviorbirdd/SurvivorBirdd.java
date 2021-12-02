package com.burak.surviviorbirdd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBirdd extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture monster;
	Texture bee1;
	Texture bee2;
	Texture bee3;
	float monsterX= 0;
	float monsterY= 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.5f;
	float enemyVelocity = 10;
	Random random;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	Circle monsterCircle;
	ShapeRenderer shapeRenderer;

	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float[] enemyOffset1 = new float[numberOfEnemies];
	float[] enemyOffset2 = new float[numberOfEnemies];
	float[] enemyOffset3 = new float[numberOfEnemies];

	float distance=0;


	Circle[] enemyCircles1;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;




	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		monster = new Texture("monster.png");
		bee1 = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");

		distance = Gdx.graphics.getWidth()/2;
		random = new Random();

		monsterX = Gdx.graphics.getWidth()/8;
		monsterY = Gdx.graphics.getHeight()/2 ;

		shapeRenderer = new ShapeRenderer();

		monsterCircle = new Circle();
		enemyCircles1 = new Circle [numberOfEnemies];
		enemyCircles2 = new Circle [numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.RED);
		font2.getData().setScale(6);

		for (int i =0; i<numberOfEnemies; i++){

			enemyOffset1[i] =(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOffset2[i] =(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOffset3[i] =(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

			enemyX[i] = Gdx.graphics.getWidth() -bee1.getWidth() /2 + i*distance;

			enemyCircles1[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();


		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState==1){


			if (enemyX[scoredEnemy]<Gdx.graphics.getWidth()/8){
				score++;

				if (scoredEnemy<numberOfEnemies -1){
					scoredEnemy++;
				}else{
					scoredEnemy =0;
				}
			}


			if(Gdx.input.justTouched()){
				velocity = -10;
			}

			for (int i=0; i<numberOfEnemies;i++){

				if (enemyX[i] < 0){
					enemyX[i] = enemyX[i] + numberOfEnemies*distance;

					enemyOffset1[i] =(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset2[i] =(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset3[i] =(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

				}else{
					enemyX[i] = enemyX[i] - enemyVelocity;

				}

				batch.draw(bee1,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset1[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(bee2,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(bee3,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

				enemyCircles1[i]= new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffset1[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCircles2[i]= new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffset2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCircles3[i]= new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffset3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

			}

			if(monsterY>0){
				velocity = velocity + gravity;
				monsterY = monsterY - velocity;
			}else{
				gameState = 2;
			}


		}else if (gameState ==0){
			if(Gdx.input.justTouched()){
				gameState =1;
			}
		}else if (gameState ==2){
			font2.draw(batch,"Game Over! Tap To Play Again",100,Gdx.graphics.getHeight()/2);

			if(Gdx.input.justTouched()){
				gameState =1;

				monsterY = Gdx.graphics.getHeight()/2 ;

				for (int i =0; i<numberOfEnemies; i++){

					enemyOffset1[i] =(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset2[i] =(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset3[i] =(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

					enemyX[i] = Gdx.graphics.getWidth() -bee1.getWidth() /2 + i*distance;

					enemyCircles1[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}
				velocity = 0;
				scoredEnemy = 0;
				score=0;

			}

		}





		batch.draw(monster,monsterX,monsterY, Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		monsterCircle.set(monsterX+Gdx.graphics.getWidth()/30,monsterY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(Color.BLACK);
//		shapeRenderer.circle(monsterCircle.x,monsterCircle.y,monsterCircle.radius);



		for (int i =0; i<numberOfEnemies; i++){

//			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffset1[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
//			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffset2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
//			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffset3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

			if (Intersector.overlaps(monsterCircle,enemyCircles1[i]) || Intersector.overlaps(monsterCircle,enemyCircles2[i]) || Intersector.overlaps(monsterCircle,enemyCircles3[i])){
				gameState = 2;

			}


		}
		shapeRenderer.end();

	}
	
	@Override
	public void dispose () {

	}
}
