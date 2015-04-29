package chbachman.api.nbt.serializers;

import net.minecraft.nbt.NBTTagCompound;
import chbachman.api.nbt.NBTBuilder;
import chbachman.api.nbt.NBTContext;
import chbachman.api.nbt.NBTSerializer;

public class PrimitiveNBT{
	
	public static void register(){
		NBTBuilder.registerNBT(Integer.class, new IntegerNBT());
		NBTBuilder.registerNBT(Short.class, new ShortNBT());
		NBTBuilder.registerNBT(Double.class, new DoubleNBT());
		NBTBuilder.registerNBT(Byte.class, new ByteNBT());
		NBTBuilder.registerNBT(Character.class, new CharNBT());
		NBTBuilder.registerNBT(Boolean.class, new BooleanNBT());
		NBTBuilder.registerNBT(String.class, new StringNBT());
		NBTBuilder.registerNBT(Float.class, new FloatNBT());
		NBTBuilder.registerNBT(Long.class, new LongNBT());
	}
	
	static class IntegerNBT implements NBTSerializer<Integer>{
		
		@Override
		public Integer loadFromNBT(NBTTagCompound d, NBTContext context){
			return d.getInteger("value");
		}

		@Override
		public void saveToNBT(Integer data, NBTTagCompound d, NBTContext context){
			d.setInteger("value", data);
		}
		
	}
	
	static class DoubleNBT implements NBTSerializer<Double>{

		@Override
		public Double loadFromNBT(NBTTagCompound d, NBTContext context){
			return d.getDouble("value");
		}

		@Override
		public void saveToNBT(Double data, NBTTagCompound d, NBTContext context){
			d.setDouble("value", data);
		}
		
	}
	
	static class ByteNBT implements NBTSerializer<Byte>{

		@Override
		public Byte loadFromNBT(NBTTagCompound d, NBTContext context){
			return d.getByte("value");
		}

		@Override
		public void saveToNBT(Byte data, NBTTagCompound d, NBTContext context){
			d.setByte("value", data);
		}
		
	}
	
	static class ShortNBT implements NBTSerializer<Short>{

		@Override
		public Short loadFromNBT(NBTTagCompound d, NBTContext context){
			return d.getShort("value");
		}

		@Override
		public void saveToNBT(Short data, NBTTagCompound d, NBTContext context){
			d.setShort("value", data);
		}
		
	}
	
	static class LongNBT implements NBTSerializer<Long>{

		@Override
		public Long loadFromNBT(NBTTagCompound d, NBTContext context){
			return d.getLong("value");
		}

		@Override
		public void saveToNBT(Long data, NBTTagCompound d, NBTContext context){
			d.setLong("value", data);
		}
		
	}
	
	static class StringNBT implements NBTSerializer<String>{

		@Override
		public String loadFromNBT(NBTTagCompound d, NBTContext context){
			return d.getString("value");
		}

		@Override
		public void saveToNBT(String data, NBTTagCompound d, NBTContext context){
			d.setString("value", data);
		}
		
	}
	
	static class CharNBT implements NBTSerializer<Character>{

		@Override
		public Character loadFromNBT(NBTTagCompound d, NBTContext context){
			return (char) d.getByte("value");
		}

		@Override
		public void saveToNBT(Character data, NBTTagCompound d, NBTContext context){
			d.setByte("value", (byte) data.charValue());
		}
		
	}
	
	static class FloatNBT implements NBTSerializer<Float>{

		@Override
		public Float loadFromNBT(NBTTagCompound d, NBTContext context){
			return d.getFloat("value");
		}

		@Override
		public void saveToNBT(Float data, NBTTagCompound d, NBTContext context){
			d.setFloat("value", data);
		}
		
	}
	
	static class BooleanNBT implements NBTSerializer<Boolean>{

		@Override
		public Boolean loadFromNBT(NBTTagCompound d, NBTContext context){
			return d.getBoolean("value");
		}

		@Override
		public void saveToNBT(Boolean data, NBTTagCompound d, NBTContext context){
			d.setBoolean("value", data);
		}
		
	}

}
