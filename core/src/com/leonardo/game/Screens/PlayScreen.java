package com.leonardo.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.leonardo.game.TheLegendOfGisclene;

/**
 * Created by PC Casa on 28/03/2016.
 */
//Classe que irá implementar os metodos de Screen
public class PlayScreen implements Screen{
    //Variavel privada para referenciar a classe TheLegendOfGisclene
    private TheLegendOfGisclene game;
    //Variavel para criar uma textura que sera desenhada
    Texture texture;

    //No construtor fazemos os valores serem atribuidos as variaveis
    //Dizemos que a texture será a badlogic.jpg
    public PlayScreen(TheLegendOfGisclene game){
        this.game = game;
        texture = new Texture("badlogic.jpg");
    }
    @Override
    public void show() {
    }

    @Override
    /*
        Primeiro Limpamos a tela com o ClearColor
        Depois usamos o BufferBit para normalizar a tela
        o batch.begin() indica que ali começa a renderização das imagens tudo que estiver entre begin, end sera desenhado;
        draw desenha a texture na posição 0, 0 da tela
     */
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(texture, 0, 0);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
