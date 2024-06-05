package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var equation:String =""
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val zero=findViewById<AppCompatButton>(R.id.zero)
        zero.setOnClickListener({
            input("0")
        })
        val one=findViewById<AppCompatButton>(R.id.one)
        one.setOnClickListener({
            input("1")
        })
        val two=findViewById<AppCompatButton>(R.id.two)
        two.setOnClickListener({
            input("2")
        })
        val three=findViewById<AppCompatButton>(R.id.three)
        three.setOnClickListener({
            input("3")
        })
        val four=findViewById<AppCompatButton>(R.id.four)
        four.setOnClickListener({
            input("4")
        })
        val five=findViewById<AppCompatButton>(R.id.five)
        five.setOnClickListener({
            input("5")
        })
        val six=findViewById<AppCompatButton>(R.id.six)
        six.setOnClickListener({
            input("6")
        })
        val seven=findViewById<AppCompatButton>(R.id.seven)
        seven.setOnClickListener({
            input("7")
        })
        val eight=findViewById<AppCompatButton>(R.id.eight)
        eight.setOnClickListener({
            input("8")
        })
        val nine=findViewById<AppCompatButton>(R.id.nine)
        nine.setOnClickListener({
            input("9")
        })
        val plus=findViewById<AppCompatButton>(R.id.plus)
        plus.setOnClickListener({
            input("+")
        })
        val minus=findViewById<AppCompatButton>(R.id.minus)
        minus.setOnClickListener({
            input("-")
        })
        val multiply=findViewById<AppCompatButton>(R.id.multiply)
        multiply.setOnClickListener({
            input("*")
        })
        val division=findViewById<AppCompatButton>(R.id.division)
        division.setOnClickListener({
            input("/")
        })
        val decimal=findViewById<AppCompatButton>(R.id.decimal)
        decimal.setOnClickListener({
            input(".")
        })
        val rightbrace=findViewById<AppCompatButton>(R.id.rightbrace)
        rightbrace.setOnClickListener({
            input("(")
        })
        val leftbrace=findViewById<AppCompatButton>(R.id.leftbrace)
        leftbrace.setOnClickListener({
            input(")")
        })
        val clearall=findViewById<AppCompatButton>(R.id.clearall)
        clearall.setOnClickListener({
            equation=""
            val inputtext=findViewById<TextView>(R.id.text1)
            inputtext.setText(equation)
            val text=findViewById<TextView>(R.id.text2)
            text.setText("")
        })
        val del=findViewById<AppCompatImageButton>(R.id.delete)
        del.setOnClickListener({
            if(equation!="")
            {
                equation=equation.substring(0,equation.length-1)
                val text=findViewById<TextView>(R.id.text1)
                text.setText(equation)
            }
        })
        val equal=findViewById<AppCompatButton>(R.id.equal)
        equal.setOnClickListener({
            val text=findViewById<TextView>(R.id.text2)
            var postfix:String=infixtopostfix(equation)
            if(postfix=="Synatx Error"){
                text.setText("Syntax Error")
            }
            else{
                var ans=calculate(postfix)
                text.setText(ans)
            }
        })

    }
    private fun input(num:String){
        equation+=num
        val inputtext=findViewById<TextView>(R.id.text1)
        inputtext.setText(equation)
    }

    private fun precedence(ch:String):Int{
        when(ch){
            "+"->return 1
            "-"->return 1
            "*"->return 2
            "/"->return 2
            "%"->return 2
        }
        return 0
    }
    private fun infixtopostfix(eqn:String):String{
        var stack=ArrayDeque<String>()
        var result=""
        for(i in 0..eqn.length-1){
            if(eqn[i]=='('){
                stack.addLast("(")
            }
            else if(eqn[i]==')'){
                if(stack.size==0){
                    return "Syntax Error"
                }
                while(stack[stack.size-1]!="("){
                    result+=","
                    result+=stack[stack.size-1]
                    stack.removeLast()
                    if(stack.size==0){
                        return "Syntax Error"
                    }
                }
                stack.removeLast()
            }
            else if((eqn[i].toInt()-48>=0&&eqn[i].toInt()-48<=9)||eqn[i]=='.'){
                if((i!=0)&&(eqn[i-1]=='+'||eqn[i-1]=='-')&&((i-1==0)||(eqn[i-2]=='+'||eqn[i-2]=='-'||eqn[i-2]=='/'||eqn[i-2]=='*'||eqn[i-2]=='%'||eqn[i-2]=='('))){
                    result+=eqn[i-1]
                }
                result+=eqn[i]
            }
            else{
                if((i!=0)&&(eqn[i-1]!='+'&&eqn[i-1]!='-'&&eqn[i-1]!='/'&&eqn[i-1]!='*'&&eqn[i-1]!='%'&&eqn[i-1]!='(')){
                    result+=","
                }
                else if((eqn[i]=='*'||eqn[i]=='/'||eqn[i]=='%')&&i!=0&&(eqn[i-1]=='+'||eqn[i-1]=='-'||eqn[i-1]=='*'||eqn[i-1]=='/'||eqn[i-1]=='%'||eqn[i-1]=='(')){
                    return "Syntax Error"
                }
                else{
                    continue
                }
                if(stack.size==0){
                    stack.addLast(eqn[i].toString())
                }
                else{
                    if(precedence(eqn[i].toString())>precedence(stack[stack.size-1])){
                        stack.addLast(eqn[i].toString())
                        continue
                    }
                    while(precedence(eqn[i].toString())<=precedence(stack[stack.size-1])){
                        result+=stack[stack.size-1]
                        result+=","
                        stack.removeLast()
                        if(stack.size==0||precedence(eqn[i].toString())>precedence(stack[stack.size-1])){
                            stack.addLast(eqn[i].toString())
                            break
                        }
                    }
                }
            }
        }
        while(stack.size!=0){
            if(stack[stack.size-1]=="("){
                return "Syntax Error"
            }
            if(result[result.length-1]!=','){
                result+=","
            }
            result+=stack[stack.size-1]
            stack.removeLast()
        }
        return result
    }
    private fun calculate(eqn:String):String{
        var stack=ArrayDeque<String>()
        var start=0
        var a:Double
        var b:Double
        var st:Boolean=false
        for(i in 0..eqn.length-1){
            if(eqn[i]==','&&(eqn[i-1]!='+'&&eqn[i-1]!='-'&&eqn[i-1]!='/'&&eqn[i-1]!='*'&&eqn[i-1]!='%')){
                var j=0
                while(eqn[i-j-1]!=','){
                    if(!st){
                        st=true
                        break
                    }
                    j++
                }
                if(start!=0){
                    start=i-j
                }
                stack.addLast(eqn.substring(start,i))
                start++
            }
            else if(i==eqn.length-1&&(i-1<0||(eqn[i-1]!='+'&&eqn[i-1]!='-'&&eqn[i-1]!='/'&&eqn[i-1]!='*'&&eqn[i-1]!='%'&&eqn[i-1]!=','))){
                if(i==0){
                    return eqn
                }
                var j=0
                while(eqn[i-j-1]!=','){
                    if(!st){
                        st=true
                        break
                    }
                    j++
                }
                if(start!=0){
                    start=i-j
                }
                stack.addLast(eqn.substring(start,i+1))
                start++
            }
            else if(i==eqn.length-1||(i!=0&&eqn[i-1]==','&&eqn[i+1]==',')&&(eqn[i]=='+'||eqn[i]=='-'||eqn[i]=='/'||eqn[i]=='*'||eqn[i]=='%')){
                if(stack.size>=2){
                b=stack.removeLast().toDouble()
                a=stack.removeLast().toDouble()
                }
                else{
                    return "Syntax Error"
                }
                when(eqn[i]){
                    '+'->stack.addLast((a+b).toString())
                    '-'->stack.addLast((a-b).toString())
                    '/'->stack.addLast((a/b).toString())
                    '*'->stack.addLast((a*b).toString())
                    '%'->stack.addLast((a%b).toString())
                }
            }
        }
        return stack.removeLast()
    }


}